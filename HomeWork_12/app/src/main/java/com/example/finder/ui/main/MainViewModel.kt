package com.example.finder.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finder.Repository
import com.example.finder.State
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Waiting)
    val state = _state.asStateFlow()

    private val _buttonVisibility = Channel<Boolean>()
    val buttonVisibility = _buttonVisibility.receiveAsFlow()

    fun onEditTextChanged(length: Int) {
        viewModelScope.launch {
            _buttonVisibility.send(length >= 3)
        }
    }

    fun onFindButtonClick(searchText: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            val result = repository.find(searchText)
            _state.value = State.Finish(result)
        }
    }
}