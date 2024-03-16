package com.example.finder

sealed class State(
    val isLoading: Boolean = false,
    open val searchText: String? = null
) {
    object Waiting : State()
    object Loading : State(isLoading = true)
    data class Finish(
        override val searchText: String?
    ) : State(
        searchText = searchText
    )
}