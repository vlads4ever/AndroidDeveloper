package com.example.marsroverphotos.ui.main

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.marsroverphotos.model.Photo
import com.example.marsroverphotos.model.Results
import com.example.marsroverphotos.R
import com.example.marsroverphotos.databinding.PictureCardBinding
import retrofit2.Response

class PictureAdapter(
    private val context: Context,
    private val onClick: (Photo?) -> Unit
) : RecyclerView.Adapter<MyPictureViewHolder>() {
    private var results: Response<Results>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPictureViewHolder {
        val binding = PictureCardBinding.inflate(LayoutInflater.from(parent.context))
        return MyPictureViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return results?.body()?.photos?.size ?: 0
    }

    override fun onBindViewHolder(holder: MyPictureViewHolder, position: Int) {
        val currentPhoto: Photo? = results?.body()?.photos?.get(position)
        if (currentPhoto != null) {
            with(holder.binding) {
                Log.d("Photo", currentPhoto.img_src)
                Glide
                    .with(holder.itemView.context)
                    .load(currentPhoto.img_src)
                    .into(picture)
                camera.text = context.getString(R.string.camera, currentPhoto.camera.name)
                earthDate.text = context.getString(R.string.earth_date, currentPhoto.earth_date)
                sol.text = context.getString(R.string.sol, currentPhoto.sol)
                rover.text = context.getString(R.string.rover, currentPhoto.rover.name)
            }
        }

        holder.binding.root.setOnClickListener {
            onClick(results?.body()?.photos?.get(position))
        }
    }

    fun setData(results: Response<Results>?) {
        if (results != null) this.results = results
        notifyDataSetChanged()
    }
}

class MyPictureViewHolder(val binding: PictureCardBinding) : RecyclerView.ViewHolder(binding.root)