package com.example.marsroverphotos.data

import android.os.Parcelable
import com.example.marsroverphotos.entity.ResultsInterface
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Results(
    @Json(name = "photos") override val photos: List<Photo>
) : ResultsInterface, Parcelable






