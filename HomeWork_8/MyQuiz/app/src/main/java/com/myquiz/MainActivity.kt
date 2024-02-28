package com.myquiz

import android.animation.AnimatorInflater
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import com.myquiz.databinding.ActivityMainBinding
import com.myquiz.quizactivity.QuizActivity

class MainActivity : AppCompatActivity() {
    private val rotationX = PropertyValuesHolder.ofFloat(View.ROTATION_X, 0f, 720f)
    private val textColor = PropertyValuesHolder.ofInt(
        "textColor",
        Color.parseColor("#FFFFFFFF"),
        Color.parseColor("#E53935")
    ).apply {
        setEvaluator(ArgbEvaluator())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ObjectAnimator.ofPropertyValuesHolder(binding.startButton, rotationX, textColor).apply {
            duration = 5000
            interpolator = AccelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }
        
        binding.startButton.setOnClickListener {
            val intent = Intent(this, QuizActivity::class.java)
            startActivity(intent)
        }
    }
}