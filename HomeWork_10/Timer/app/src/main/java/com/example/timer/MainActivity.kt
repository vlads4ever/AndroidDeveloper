package com.example.timer

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.timer.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

const val TIMER_PROGRESS = "timerProgress"
class MainActivity : AppCompatActivity() {
    private var userNumber = 0
    private var job: Job? = null
    private var isRunning = false
    private lateinit var binding: ActivityMainBinding
    private var timerValue: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        savedInstanceState?.let {
            timerValue = it.getInt(TIMER_PROGRESS)
            Toast.makeText(this, "$timerValue", Toast.LENGTH_SHORT).show()
            if (timerValue != null) {
                isRunning = false
                updateUI(AppState.Prepare())
                actionTimer(timerValue!!)
                timerValue = null
            }
        }

        binding.sliderBar.addOnChangeListener { _, _, _ -> updateUI(AppState.Prepare()) }

        binding.actionButton.setOnClickListener {
            actionTimer(userNumber)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        job?.cancel()
        if (timerValue != null) outState.putInt(TIMER_PROGRESS, timerValue!!)
        super.onSaveInstanceState(outState)
    }

    private fun updateUI(state: AppState, timerValue: Int? = null) {
        when (state) {
            is AppState.Prepare -> {
                userNumber = binding.sliderBar.value.toInt()
                binding.timerNumber.text = userNumber.toString()
                binding.progressBar.max = binding.sliderBar.value.toInt()
                binding.progressBar.progress = binding.sliderBar.value.toInt()
                isRunning = false
            }
            is AppState.Run -> {
                isRunning = true
                binding.actionButton.setText(R.string.stop_timer)
                binding.sliderBar.isEnabled = false
            }
            is AppState.Stop -> {
                isRunning = false
                binding.timerNumber.text = binding.sliderBar.value.toInt().toString()
                binding.progressBar.progress = userNumber
                binding.actionButton.setText(R.string.start_timer)
                binding.sliderBar.isEnabled = true
                Toast.makeText(this, "Timer has stopped", Toast.LENGTH_SHORT).show()

            }
            is AppState.Finish -> {
                isRunning = false
                binding.timerNumber.text = binding.sliderBar.value.toInt().toString()
                binding.progressBar.progress = userNumber
                binding.actionButton.setText(R.string.start_timer)
                binding.sliderBar.isEnabled = true
                Toast.makeText(this, "Timer has ended", Toast.LENGTH_SHORT).show()
            }
            is AppState.UpdateProgress -> {
                binding.timerNumber.text = timerValue.toString()
                binding.progressBar.progress = timerValue.toString().toInt()
            }
        }
    }


    private fun actionTimer(countNumber: Int) {
        if (!isRunning) {
            updateUI(AppState.Run())
            job = CoroutineScope(Dispatchers.Main).launch {
                for (timerValue in countNumber downTo 1) {
                    updateUI(AppState.UpdateProgress(), timerValue)
                    delay(1000)
                    this@MainActivity.timerValue = timerValue
                }
                updateUI(AppState.Finish())
                cancel()
            }
        } else {
            job?.cancel()
            updateUI(AppState.Stop())
        }
    }


    sealed class AppState {
        class Stop : AppState()
        class Run : AppState()
        class Finish : AppState()
        class Prepare : AppState()
        class UpdateProgress : AppState()
    }
}