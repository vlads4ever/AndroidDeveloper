package com.example.attractions.data

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class Repository @Inject constructor() {
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val featureCollection: FeatureCollectionApi = retrofit.create(FeatureCollectionApi::class.java)

    companion object {
        private const val BASE_URL = "https://api.opentripmap.com/0.1/ru/places/"
    }
}

interface FeatureCollectionApi {
    @GET("radius")
    suspend fun getFeatureCollection(
        @Query("radius") radius: Int,
        @Query("lon") lon: Double,
        @Query("lat") lat: Double,
        @Query("apikey") apikey: String,
        ): Response<FeatureCollection>?
}