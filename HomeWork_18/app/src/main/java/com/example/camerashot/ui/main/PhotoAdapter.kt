package com.example.camerashot.ui.main

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.camerashot.data.Photo
import com.example.camerashot.databinding.PhotoCardBinding

class PhotoAdapter : RecyclerView.Adapter<PhotoHolder>() {
    private var photos: List<Photo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoHolder {
        val binding = PhotoCardBinding.inflate(LayoutInflater.from(parent.context))
        return PhotoHolder(binding)
    }

    override fun getItemCount(): Int = photos.size

    override fun onBindViewHolder(holder: PhotoHolder, position: Int) {
        val currentPhoto = photos[position]

        with (holder.binding) {
            Glide
                .with(holder.itemView.context)
                .load(Uri.parse(currentPhoto.photoUri))
                .into(photoView)
            photoDateText.text = currentPhoto.date
        }
    }

    fun setData(photos: List<Photo>) {
        this.photos = photos
        notifyDataSetChanged()
    }
}

class PhotoHolder(val binding: PhotoCardBinding) : RecyclerView.ViewHolder(binding.root)