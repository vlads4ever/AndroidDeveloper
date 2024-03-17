package com.example.finder

sealed class State(
    open val searchText: String? = null
) {
    object Waiting : State()
    object Loading : State()
    data class Finish(
        override val searchText: String?
    ) : State(
        searchText = searchText
    )
}