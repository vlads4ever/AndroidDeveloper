package com.example.datarepository

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.datarepository.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository(this)
        val enteredText = binding.enteredText
        binding.displayedText.text = repository.getText()

        binding.saveButton.setOnClickListener {
            repository.saveText(enteredText.text.toString())
            binding.displayedText.text = repository.getText()
        }
        binding.clearButton.setOnClickListener {
            repository.clearText()
            binding.displayedText.text = repository.getText()
        }
    }
}