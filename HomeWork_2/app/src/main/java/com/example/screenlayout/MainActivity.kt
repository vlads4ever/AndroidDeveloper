package com.example.screenlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.screenlayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text1.text = "Верхняя строчка, настроенная из кода"
        binding.text2.text = "Нижняя строчка, настроенная из кода"
    }
}