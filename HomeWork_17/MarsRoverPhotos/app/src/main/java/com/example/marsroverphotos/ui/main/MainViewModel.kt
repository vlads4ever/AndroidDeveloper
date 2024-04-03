package com.example.marsroverphotos.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsroverphotos.repository.Repository
import com.example.marsroverphotos.model.Results
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

private const val API_KEY = "qT6TEfQCcxZ88ea5dYODQDC48PTICRfRHFkXqF4y"
private const val SOL = 1000

class MainViewModel(
    private val repository: Repository,
    private val date: String? = null
) : ViewModel() {

    private var _photosStateFlow = MutableStateFlow<Response<Results>?>(null)
    val photosStateFlow = _photosStateFlow.asStateFlow()
    private var _errorMessageFlow = MutableStateFlow<String?>(null)
    val errorMessageFlow = _errorMessageFlow.asStateFlow()

    suspend fun getPhotosList() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                if (date != null) {
                    repository.getMarsPhotosByDate.getPictures(date, API_KEY)
                } else {
                    repository.getMarsPhotos.getPictures(SOL, API_KEY)
                }
            }.fold(
                onSuccess = { _photosStateFlow.value = it },
                onFailure = { _errorMessageFlow.value = it.message }
            )
        }
    }
}