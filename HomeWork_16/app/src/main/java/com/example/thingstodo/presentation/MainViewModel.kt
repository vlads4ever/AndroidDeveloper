package com.example.thingstodo.presentation

import androidx.lifecycle.ViewModel
import com.example.thingstodo.domain.GetUsefulActivityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getUsefulActivityUseCase: GetUsefulActivityUseCase
) : ViewModel() {
    private val _activityFlow = MutableStateFlow<State?>(null)
    val activityFlow = _activityFlow.asStateFlow()

    suspend fun reloadUsefulActivity() {
        try {
            val usefulActivity = getUsefulActivityUseCase.execute()
            _activityFlow.value = State.Success(value = usefulActivity.activity.toString())
        } catch (e: Exception) {
            _activityFlow.value = State.Failure(value = e.message.toString())
        }
    }
}