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

private const val TIMER_PROGRESS = "timerProgress"
private const val RUNNING_STATUS = "runningStatus"
private const val COUNTDOWN = "userNumber"

class MainActivity : AppCompatActivity() {
    private var userNumber = 20
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
            isRunning = it.getBoolean(RUNNING_STATUS)
            userNumber = it.getInt(COUNTDOWN)
            if (isRunning) {
                updateUI(AppState.Continue())
                actionTimer(timerValue!!)
                timerValue = null
            } else {
                updateUI(AppState.Prepare())
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
        outState.putBoolean(RUNNING_STATUS, isRunning)
        outState.putInt(COUNTDOWN, userNumber)
        super.onSaveInstanceState(outState)
    }

    private fun updateUI(state: AppState, timerValue: Int? = null) {
        when (state) {
            is AppState.Prepare -> {
                userNumber = binding.sliderBar.value.toInt()
                binding.timerNumber.text = userNumber.toString()
                binding.progressBar.max = userNumber
                binding.progressBar.progress = userNumber
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
            is AppState.Continue -> {
                binding.timerNumber.text = this@MainActivity.timerValue.toString()
                binding.progressBar.max = userNumber
                binding.progressBar.progress = this@MainActivity.timerValue!!
                isRunning = false
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
        class Stop: AppState()
        class Run: AppState()
        class Finish: AppState()
        class Prepare: AppState()
        class UpdateProgress: AppState()
        class Continue: AppState()
    }
}