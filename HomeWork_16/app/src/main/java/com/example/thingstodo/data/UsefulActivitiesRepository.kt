package com.example.thingstodo.data

import android.util.Log
import com.example.thingstodo.entity.UsefulActivity
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import javax.inject.Inject

private const val BASE_URL = "https://www.boredapi.com/api/"

class UsefulActivitiesRepository @Inject constructor(){
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    suspend fun getUsefulActivity(): UsefulActivity {
        val receivedUsefulActivity = retrofit.create(GetUsefulActivity::class.java)
        val usefulActivity = UsefulActivityDto(
            receivedUsefulActivity.getUsefulActivity().body()?.activity,
            receivedUsefulActivity.getUsefulActivity().body()?.type,
            receivedUsefulActivity.getUsefulActivity().body()?.participants,
            receivedUsefulActivity.getUsefulActivity().body()?.price,
            receivedUsefulActivity.getUsefulActivity().body()?.link,
            receivedUsefulActivity.getUsefulActivity().body()?.key,
            receivedUsefulActivity.getUsefulActivity().body()?.accessibility
        )
        return usefulActivity
    }
}

interface GetUsefulActivity {
    @GET("activity/")
    suspend fun getUsefulActivity(): Response<UsefulActivityDto>
}