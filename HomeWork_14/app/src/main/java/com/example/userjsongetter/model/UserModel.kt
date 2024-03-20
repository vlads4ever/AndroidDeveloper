package com.example.userjsongetter.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

class UserModel {
    @Parcelize
    class UserModel(
        var firstName: String?,
        var lastName: String?,
        var email: String?,
        var birthday: String?,
        var photoUrl: String?
    ) : Parcelable

    @JsonClass(generateAdapter = true)
    data class Results(
        @Json(name = "results") val results: List<User>
    )

    data class User(
        @Json(name = "name") val name: Name,
        @Json(name = "email") val email: String,
        @Json(name = "dob") val dob: Dob,
        @Json(name = "picture") val picture: Picture
    )

    data class Dob(
        @Json(name = "date") val date: String
    )

    data class Name(
        @Json(name = "first") val first: String,
        @Json(name = "last") val last: String
    )

    data class Picture(
        @Json(name = "large") val large: String
    )
}