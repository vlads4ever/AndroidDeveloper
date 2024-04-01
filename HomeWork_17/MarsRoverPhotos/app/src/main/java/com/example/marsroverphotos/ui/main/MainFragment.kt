package com.example.marsroverphotos.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.marsroverphotos.model.Photo
import com.example.marsroverphotos.repository.Repository

import com.example.marsroverphotos.R
import com.example.marsroverphotos.databinding.FragmentMainBinding
import kotlinx.coroutines.launch

private const val PHOTO = "photo"

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding?  = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    return MainViewModel(Repository(context?.applicationContext)) as T
                } else {
                    throw IllegalArgumentException("")
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pictureAdapter = PictureAdapter(requireContext()) { photo -> onClick(photo!!) }
        binding.pictureRecycler.adapter = pictureAdapter
        binding.pictureRecycler.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getPhotosList()
            viewModel.photosStateFlow.collect { results ->
                Log.d("Fragment", (results == null).toString())
                if (results != null) pictureAdapter.setData(results)
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.errorMessageFlow.collect {error ->
                if (error != null) Toast.makeText(
                    requireContext(),
                    error.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun onClick(photo: Photo) {
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