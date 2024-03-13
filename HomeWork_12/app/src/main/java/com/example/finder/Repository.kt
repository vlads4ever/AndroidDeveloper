package com.example.finder

import kotlinx.coroutines.delay

class Repository {
    suspend fun find(searchText: String): String {
        delay(5000)
        return searchText
    }
}