package com.example.camerashot.ui.main

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.camerashot.data.Repository

class MainViewModel(
    val context: Context,
    val repository: Repository
) : ViewModel()