package com.example.marsroverphotos.data

import android.os.Parcelable
import com.example.marsroverphotos.entity.PhotoInterface
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    @Json(name = "id") override val id: Int,
    @Json(name = "sol") override val sol: Int,
    @Json(name = "camera") override val camera: Camera,
    @Json(name = "img_src") override val img_src: String,
    @Json(name = "earth_date") override val earth_date: String,
    @Json(name = "rover") override val rover: Rover
) : PhotoInterface, Parcelable
