package com.example.attractions.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Feature(
    @Json(name = "id") val id: String,
    @Json(name = "geometry") val geometry: Point,
    @Json(name = "properties") val properties: Property
) : Parcelable
