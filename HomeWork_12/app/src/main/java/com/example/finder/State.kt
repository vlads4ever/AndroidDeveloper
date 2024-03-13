package com.example.finder

sealed class State {
    object Waiting : State()
    object Loading : State()
    data class Finish(val searchText: String) : State()
}