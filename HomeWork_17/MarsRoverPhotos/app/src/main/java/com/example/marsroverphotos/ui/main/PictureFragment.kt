package com.example.marsroverphotos.ui.main

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.m17_recyclerview.ui.main.Photo
import com.example.marsroverphotos.R
import com.example.marsroverphotos.databinding.FragmentPictureBinding

private const val PHOTO = "photo"

class PictureFragment : Fragment() {

    private lateinit var binding: FragmentPictureBinding
    private var _photo: Photo? = null
    private var photo = _photo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPictureBinding.inflate(layoutInflater)
        arguments?.let {
            photo = it.getParcelable(PHOTO)!!
        }
        if (photo != null) {
            Log.d("Photo", photo!!.img_src)
            Glide
                .with(this@PictureFragment)
                .load(photo!!.img_src)
                .into(binding.picture)
            binding.camera.text = requireContext().getString(R.string.camera, photo!!.camera.name)
            binding.earthDate.text =
                requireContext().getString(R.string.earth_date, photo!!.earth_date)
            binding.sol.text = requireContext().getString(R.string.sol, photo!!.sol)
            binding.rover.text = requireContext().getString(R.string.rover, photo!!.rover.name)
        }
        return binding.root
    }
}