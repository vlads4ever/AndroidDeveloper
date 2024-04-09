package com.example.attractions.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.attractions.App
import com.example.attractions.data.FeatureCollection
import com.example.attractions.data.Repository
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.yandex.mapkit.geometry.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val repository: Repository
) : ViewModel() {
    private var _geoLocationFlow = MutableStateFlow<Point?>(null)
    val geoLocationFlow = _geoLocationFlow.asStateFlow()

    private var _featureCollectionFlow = MutableStateFlow<FeatureCollection?>(null)
    val featureCollectionFlow = _featureCollectionFlow.asStateFlow()

    private var _errorMessageFlow = MutableStateFlow<String?>(null)
    val errorMessageFlow = _errorMessageFlow.asStateFlow()

    private var longitude: Double = 0.0
    private var latitude: Double = 0.0
    val fusedClient = LocationServices.getFusedLocationProviderClient(context)
    private val request = LocationRequest.Builder(
        Priority.PRIORITY_HIGH_ACCURACY,
        1000
    ).build()

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {
            super.onLocationResult(p0)
            longitude = p0.lastLocation!!.longitude
            latitude = p0.lastLocation!!.latitude
            _geoLocationFlow.value = Point(latitude, longitude)
        }
    }

    fun getFeatureCollectionFlow(point: Point) {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                repository.featureCollection.getFeatureCollection(
                    1000,
                    point.longitude,
                    point.latitude,
                    App.OPEN_TRIP_MAP_API_KEY
                )
            }.fold(
                onSuccess = { response ->
                    if (response!!.isSuccessful) {
                        _featureCollectionFlow.value = response.body()
                    }
                },
                onFailure = {
                    _errorMessageFlow.value = it.message
                }
            )
        }
    }

    @SuppressLint("MissingPermission")
    fun startLocation() {
        fusedClient.requestLocationUpdates(
            request,
            locationCallback,
            Looper.getMainLooper()
        )
    }
}