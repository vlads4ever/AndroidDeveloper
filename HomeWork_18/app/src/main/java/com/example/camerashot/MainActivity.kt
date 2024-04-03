package com.example.camerashot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.camerashot.databinding.ActivityMainBinding
import com.example.camerashot.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}