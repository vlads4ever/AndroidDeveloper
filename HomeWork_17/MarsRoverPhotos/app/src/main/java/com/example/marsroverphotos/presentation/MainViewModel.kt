package com.example.marsroverphotos.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.marsroverphotos.data.Results
import com.example.marsroverphotos.domain.GetMarsPhotosByDateUseCase
import com.example.marsroverphotos.domain.GetMarsPhotosUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject



class MainViewModel @Inject constructor(
    private val getMarsPhotosByDateUseCase: GetMarsPhotosByDateUseCase,
    private val getMarsPhotosUseCase: GetMarsPhotosUseCase
) : ViewModel() {
    private var _date = MutableStateFlow<String?>(null)
    val date = _date.asStateFlow()

    private var _photosStateFlow = MutableStateFlow<Response<Results>?>(null)
    val photosStateFlow = _photosStateFlow.asStateFlow()

    private var _errorMessageFlow = MutableStateFlow<String?>(null)
    val errorMessageFlow = _errorMessageFlow.asStateFlow()

    private val _stateLoad = MutableStateFlow(false)
    val stateLoad = _stateLoad.asStateFlow()

    suspend fun getPhotosList() {
        viewModelScope.launch(Dispatchers.IO) {
            kotlin.runCatching {
                _stateLoad.value = true
                if (_date.value != null) {
                    getMarsPhotosByDateUseCase.execute(_date.value!!)
                } else {
                    getMarsPhotosUseCase.execute()
                }
            }.fold(
                onSuccess = {
                    _photosStateFlow.value = it
                    _stateLoad.value = false
                },
                onFailure = {
                    _errorMessageFlow.value = it.message
                    _stateLoad.value = false
                }
            )
        }
    }

    fun setDate(date: String?) {
        this._date.value = date
    }
}