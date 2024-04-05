package com.example.marsroverphotos.data

import android.os.Parcelable
import com.example.marsroverphotos.entity.CameraInterface
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Camera(
    @Json(name = "id") override val id: Int,
    @Json(name = "name") override val name: String,
    @Json(name = "rover_id") override val rover_id: Int,
    @Json(name = "full_name") override val full_name: String
) : CameraInterface, Parcelable
