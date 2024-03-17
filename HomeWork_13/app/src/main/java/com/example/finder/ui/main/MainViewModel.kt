package com.example.finder.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finder.Repository
import com.example.finder.State
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class MainViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Waiting)
    val state = _state.asStateFlow()

    private val searchingString = MutableStateFlow<String>("")
    private var searchJob: Job? = null

    private suspend fun searchString() {
        _state.value = State.Loading
        val result = repository.find(searchingString.value)
        _state.value = State.Finish(result)
    }

    @OptIn(FlowPreview::class)
    fun onEditTextChanged(str: String) {
        searchJob?.cancel()
        if (str.length < 3)
            _state.value = State.Waiting
        else {
            searchingString.value = str
            searchJob = searchingString
                .debounce(300)
                .onEach {
                    searchString()
                }.launchIn(viewModelScope)
        }
    }

}