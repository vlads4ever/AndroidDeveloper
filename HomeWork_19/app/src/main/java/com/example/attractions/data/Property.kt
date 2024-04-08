package com.example.attractions.data

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.parcelize.Parcelize

@Parcelize
data class Property(
    @Json(name = "xid") val xid: String,
    @Json(name = "name") val name: String,
    @Json(name = "dist") val dist: Float,
    @Json(name = "rate") val rate: Int,
    @Json(name = "kinds") val kinds: String,
) : Parcelable
