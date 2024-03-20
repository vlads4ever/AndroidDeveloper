package com.example.userjsongetter.ui.main

import androidx.lifecycle.ViewModel
import com.example.userjsongetter.model.UserModel.UserModel

class MainViewModel(
    private val repository: MainRepository
) : ViewModel() {
    suspend fun getJson(): UserModel {
        val result = repository.searchRandomUser.getRandomUser()
        return UserModel(
            result.body()?.results?.first()?.name?.first,
            result.body()?.results?.first()?.name?.last,
            result.body()?.results?.first()?.email,
            result.body()?.results?.first()?.dob?.date,
            result.body()?.results?.first()?.picture?.large
        )
    }
}