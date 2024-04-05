package com.example.marsroverphotos.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.marsroverphotos.R
import com.example.marsroverphotos.data.Photo
import com.example.marsroverphotos.databinding.FragmentPictureBinding

private const val PHOTO = "photo"

class PictureFragment : Fragment() {
    private var _binding: FragmentPictureBinding? = null
    private val binding get() = _binding!!
    private var _photo: Photo? = null
    private val photo get() = _photo!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPictureBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            _photo = it.getParcelable(PHOTO)!!
        }

        Log.d("Photo", photo.img_src)
        Glide
            .with(this@PictureFragment)
            .load(photo.img_src)
            .into(binding.picture)
        binding.camera.text = requireContext().getString(R.string.camera, photo.camera.name)
        binding.earthDate.text =
            requireContext().getString(R.string.earth_date, photo.earth_date)
        binding.sol.text = requireContext().getString(R.string.sol, photo.sol)
        binding.rover.text = requireContext().getString(R.string.rover, photo.rover.name)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}