package com.example.finder.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finder.Repository
import com.example.finder.State
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel(
    private val repository: Repository
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Waiting)
    val state = _state.asStateFlow()

    private val _searchString = MutableStateFlow<String>("")
    lateinit var searchString: String
    val job = _searchString.debounce(300).onEach{

    }.launchIn(viewModelScope)

//    private val _buttonVisibility = Channel<Boolean>()
//    val buttonVisibility = _buttonVisibility.receiveAsFlow()

//    fun onEditTextChanged(length: Int) {
//        viewModelScope.launch {
//            _buttonVisibility.send(length >= 3)
//        }
//    }

    fun onEditTextChanged(searchText: String) {
        viewModelScope.launch {

            _searchString.debounce(300).onEach{}.launchIn(viewModelScope)
            _searchString.value = searchText
            findText(searchString)
        }
    }

    fun findText(searchText: String) {
        viewModelScope.launch {
            _state.value = State.Loading
            val result = repository.find(searchText)
            _state.value = State.Finish(result)
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