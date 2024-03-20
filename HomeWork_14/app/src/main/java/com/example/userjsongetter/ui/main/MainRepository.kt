package com.example.userjsongetter.ui.main

import retrofit2.Retrofit

private const val BASE_URL = "https://randomuser.me"
class MainRepository {
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(Mosh)
}