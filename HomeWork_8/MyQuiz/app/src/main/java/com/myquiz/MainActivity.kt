package com.myquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import com.myquiz.databinding.ActivityMainBinding
import com.myquiz.quizactivity.QuizActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Создание анимации появления этой Activity
        with(window) {
            enterTransition = Slide(Gravity.BOTTOM)
            exitTransition = Slide(Gravity.END)
        }

        binding.startButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}