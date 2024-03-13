package com.example.finder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finder.ui.main.MainViewModel
import java.lang.IllegalArgumentException

class MainViewModelFactory: ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(Repository()) as T
        } else {
            throw IllegalArgumentException("Unknown class name")
        }
    }
}