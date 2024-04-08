package com.example.attractions.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Point(
    @Json(name = "coordinates") val coordinates: List<Double>
) : Parcelable
