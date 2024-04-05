package com.example.marsroverphotos.data

import com.squareup.moshi.Moshi
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

private const val BASE_URL = "https://api.nasa.gov"

class Repository @Inject constructor() {
    private val moshi =
        Moshi.Builder().addLast(com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory())
            .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val getMarsPhotos: MarsPhotosApi = retrofit.create(MarsPhotosApi::class.java)
}

interface MarsPhotosApi {
    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getPictures(
        @Query("sol") sol: Int = SOL,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Results>?

    @GET("/mars-photos/api/v1/rovers/curiosity/photos")
    suspend fun getPicturesByDate(
        @Query("earth_date") earthDate: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Results>?

    companion object {
        const val API_KEY = "qT6TEfQCcxZ88ea5dYODQDC48PTICRfRHFkXqF4y"
        const val SOL = 1000
    }
}