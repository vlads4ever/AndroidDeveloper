package com.example.marsroverphotos.presentation

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.marsroverphotos.R
import com.example.marsroverphotos.databinding.FragmentMainBinding
import com.example.marsroverphotos.entity.PhotoInterface
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val PHOTO = "photo"
private const val DATE = "date"

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding?  = null
    private val binding get() = _binding!!
    private var date: String? = null

    @Inject
    lateinit var mainViewModelFactory: MainViewModelFactory
    private val viewModel: MainViewModel by viewModels { mainViewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            date = it.getString(DATE)
        }

        val pictureAdapter = PictureAdapter(requireContext()) { photo -> onClick(photo!!) }
        binding.pictureRecycler.adapter = pictureAdapter
        binding.pictureRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        binding.settingsButton.setOnClickListener {
            findNavController()
                .navigate(resId = R.id.action_mainFragment_to_settingFragment, args = null)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.setDate(date)
            viewModel.getPhotosList()
            viewModel.photosStateFlow.collect { results ->
                Log.d("Fragment", results.toString())
                if (results != null) pictureAdapter.setData(results)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessageFlow.collect { error ->
                if (error != null) Toast.makeText(
                    requireContext(),
                    error.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.stateLoad.collect { loadState ->
                    binding.progressBar.isVisible = loadState
            }
        }
    }

    private fun onClick(photo: PhotoInterface) {
        val bundle = Bundle().apply {
            putParcelable(PHOTO, photo)
        }
        findNavController()
            .navigate(resId = R.id.action_mainFragment_to_pictureFragment, args = bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}