package com.example.marsroverphotos.repository

import android.content.Context
import com.example.marsroverphotos.model.Results
import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.nasa.gov"

class Repository(applicationContext: Context?) {
    private val moshi =
        Moshi.Builder().addLast(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val getMarsPhotos: MarsPhotosApi = retrofit.create(MarsPhotosApi::class.java)
    val getMarsPhotosByDate: MarsPhotosByDateApi = retrofit.create(MarsPhotosByDateApi::class.java)
}

interface MarsPhotosApi {
    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getPictures(
        @Query("sol") sol: Int,
        @Query("api_key") apiKey: String
    ): Response<Results>?
}

interface MarsPhotosByDateApi {
    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getPictures(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String
    ): Response<Results>?
}