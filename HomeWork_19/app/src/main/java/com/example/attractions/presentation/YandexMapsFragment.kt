package com.example.attractions.presentation

import android.animation.AnimatorInflater
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.attractions.R
import com.example.attractions.data.FeatureCollection
import com.example.attractions.data.PointUserData
import com.example.attractions.databinding.FragmentYandexMapsBinding
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class YandexMapsFragment : Fragment() {
    private var _binding: FragmentYandexMapsBinding?  = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var mapView: MapView
    private lateinit var mapKit: MapKit
    private lateinit var currentLocation: Point
    private var markersList = mutableListOf<PlacemarkMapObject>()
    private lateinit var mapObjects: MapObjectCollection

    private var isDescriptionOpened = false
    private var fromState = false

//    @Inject
//    lateinit var mainViewModelFactory: MainViewModelFactory
//    private val viewModel: MainViewModel by viewModels { mainViewModelFactory }

private val launcher =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions.values.all { it }) {
            viewModel.startLocation()
        }
    }

    private val pointTapListener = MapObjectTapListener { mapObject, _ ->
        val extractedPointUserData = mapObject.userData
        if (extractedPointUserData is PointUserData) {
            binding.xid.text = extractedPointUserData.xid
            binding.name.text = extractedPointUserData.name
        }
        openDescriptionCard()
        true
    }

    private val mapTapListener = object : InputListener {
        override fun onMapTap(p0: Map, p1: Point) {
            closeDescriptionCard()
        }

        override fun onMapLongTap(p0: Map, p1: Point) {  }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(requireContext())
        mapView = binding.mapView
        checkPermissions()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapKit = MapKitFactory.getInstance()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        viewModel.fusedClient.removeLocationUpdates(viewModel.locationCallback)
        super.onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentYandexMapsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = mapKit.createUserLocationLayer(mapView.mapWindow)
        user.isVisible = true
        mapObjects = mapView.mapWindow.map.mapObjects.addCollection()
        mapView.mapWindow.map.addInputListener(mapTapListener)

        savedInstanceState?.let { bundle ->
            val screenState = bundle.getParcelable<ScreenState>(SCREEN_STATE)
            if (screenState != null) {
                mapView.mapWindow.map.move(
                    CameraPosition(
                        Point(screenState.latitude, screenState.longitude),
                        screenState.zoom,
                        screenState.azimuth,
                        screenState.tilt
                    )
                )
                if (screenState.isDescriptionOpened) {
                    binding.name.text = screenState.name
                    binding.xid.text = screenState.xid
                    openDescriptionCard()
                }
                fromState = true
            }
        }

        binding.closeDescription.setOnClickListener {
            closeDescriptionCard()
        }

        binding.getLocation.setOnClickListener {
            if (this@YandexMapsFragment::currentLocation.isInitialized) {
                mapView.mapWindow.map.move(
                    CameraPosition(
                        currentLocation,
                        mapView.mapWindow.map.cameraPosition.zoom,
                        mapView.mapWindow.map.cameraPosition.azimuth,
                        mapView.mapWindow.map.cameraPosition.tilt,
                    ),
                    Animation(Animation.Type.SMOOTH, 1.0f),
                    null
                )
            }
        }

        binding.zoomIn.setOnClickListener {
            mapView.mapWindow.map.move(
                CameraPosition(
                    mapView.mapWindow.map.cameraPosition.target,
                    mapView.mapWindow.map.cameraPosition.zoom.inc(),
                    mapView.mapWindow.map.cameraPosition.azimuth,
                    mapView.mapWindow.map.cameraPosition.tilt
                ),
                Animation(Animation.Type.SMOOTH, 0.1f),
                null
            )
        }

        binding.zoomOut.setOnClickListener {
            mapView.mapWindow.map.move(
                CameraPosition(
                    mapView.mapWindow.map.cameraPosition.target,
                    mapView.mapWindow.map.cameraPosition.zoom.dec(),
                    mapView.mapWindow.map.cameraPosition.azimuth,
                    mapView.mapWindow.map.cameraPosition.tilt
                ),
                Animation(Animation.Type.SMOOTH, 0.1f),
                null
            )
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.geoLocationFlow.asLiveData().observe(viewLifecycleOwner) { point ->
                if (point != null) {
                    if (!this@YandexMapsFragment::currentLocation.isInitialized) {
                        currentLocation = point
                        if (!fromState) {
                            mapView.mapWindow.map.move(
                                CameraPosition(currentLocation, 15.0f, 0.0f, 0.0f),
                                Animation(Animation.Type.SMOOTH, 2.0f),
                                null
                            )
                        }
                        viewModel.getFeatureCollectionFlow(currentLocation)
                    } else {
                        currentLocation = point
                    }
                }
            }

            viewModel.featureCollectionFlow.asLiveData().observe(viewLifecycleOwner) { featureCollection ->
                if (featureCollection != null) addFeatureMarker(featureCollection)
            }
            viewModel.errorMessageFlow.asLiveData().observe(viewLifecycleOwner) { error ->
                if (!error.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun addFeatureMarker(featureCollection: FeatureCollection) {
        featureCollection.features.forEach { feature ->
            val marker = mapView.mapWindow.map.mapObjects.addPlacemark(
                Point(feature.geometry.coordinates[1], feature.geometry.coordinates[0])
            ).also {
                it.setIconStyle(IconStyle().setScale(5.0f))
            }
            marker.userData = PointUserData(feature.properties.xid, feature.properties.name)
            marker.addTapListener(pointTapListener)
            markersList.add(marker)
        }
    }

    private fun checkPermissions() {
        if (permissions.all { permission ->
            ContextCompat.checkSelfPermission(
                requireContext(),
                permission
            ) == PackageManager.PERMISSION_GRANTED
            }) {
            viewModel.startLocation()
        } else {
            launcher.launch(permissions)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val screenState = ScreenState(
            currentLocation.latitude,
            currentLocation.longitude,
            mapView.mapWindow.map.cameraPosition.zoom,
            mapView.mapWindow.map.cameraPosition.azimuth,
            mapView.mapWindow.map.cameraPosition.tilt,
            isDescriptionOpened,
            binding.xid.text.toString(),
            binding.name.text.toString()
        )
        outState.putParcelable(SCREEN_STATE, screenState)
        super.onSaveInstanceState(outState)
    }

    private fun openDescriptionCard() {
        isDescriptionOpened = true
        val animation = AnimatorInflater.loadAnimator(requireContext(), R.animator.slide_up)
        animation.setTarget(binding.descriptionCard)
        animation.start()
    }

    private fun closeDescriptionCard() {
        isDescriptionOpened = false
        val animation = AnimatorInflater.loadAnimator(requireContext(), R.animator.slide_down)
        animation.setTarget(binding.descriptionCard)
        animation.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val SCREEN_STATE = "screenState"
    }
}