package com.example.marsroverphotos.data

import android.os.Parcelable
import com.example.marsroverphotos.entity.RoverInterface
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Rover(
    @Json(name = "id") override val id: Int,
    @Json(name = "name") override val name: String,
    @Json(name = "landing_date") override val landing_date: String,
    @Json(name = "launch_date") override val launch_date: String,
    @Json(name = "status") override val status: String
) : RoverInterface, Parcelable
