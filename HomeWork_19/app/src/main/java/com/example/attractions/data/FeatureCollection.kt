package com.example.attractions.data

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class FeatureCollection(
    @Json(name = "features") val features: List<Feature>
) : Parcelable
