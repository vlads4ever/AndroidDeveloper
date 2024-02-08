package com.example.screenlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.screenlayout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.customView.setFirstText(getString(R.string.first_text))
        binding.customView.setSecondText(getString(R.string.second_text))
    }
}