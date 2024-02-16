package com.example.userprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.annotation.SuppressLint
import androidx.core.widget.doOnTextChanged
import com.example.userprofile.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private val progress = Random.nextInt(101)
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding
        get() {
            return _binding!!
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        runProgram()
    }

    @SuppressLint("SetTextI18n")
    private fun runProgram() {
        checkSaveButtonAvailability()
        checkNotification(false)
        binding.progressScores.progress = progress
        binding.countScores.text = progress.toString() + resources.getString(R.string.count_score)
        changeEditPhoneText()
        changeChoiceNotifications()
        changeRadioButton()
        changeNotificationAuthorization()
    }

    private fun changeEditPhoneText() {
        binding.editPhoneText.doOnTextChanged { _, _, _, _ ->
            checkSaveButtonAvailability()
        }
    }

    private fun changeRadioButton() {
        binding.radioGroup.setOnCheckedChangeListener { _, _ ->
            checkSaveButtonAvailability()
        }
    }

    private fun changeChoiceNotifications() {
        binding.enableNotifications.setOnCheckedChangeListener { _, isChecked ->
            checkNotification(isChecked)
            checkSaveButtonAvailability()
        }
    }

    private fun checkNotification(isChecked: Boolean) {
        binding.notificationAuthorization.isEnabled = isChecked
        binding.notificationNewProducts.isEnabled = isChecked
        if (!isChecked) {
            binding.notificationAuthorization.isChecked = false
            binding.notificationNewProducts.isChecked = false
        }
        checkSaveButtonAvailability()
    }

    private fun checkInputName(): Boolean {
        return !(binding.editNameText.text.isNullOrEmpty())
    }

    private fun checkRadioButton(): Boolean {
        return binding.radioButtonMan.isChecked || binding.radioButtonWoman.isChecked
    }

    private fun checkNotifications(): Boolean {
        if (binding.enableNotifications.isChecked && binding.notificationAuthorization.isChecked
            || binding.enableNotifications.isChecked && binding.notificationNewProducts.isChecked) {
            return true
        } else if (!binding.enableNotifications.isChecked) {
            return true
        }
        return false
    }

    private fun changeNotificationAuthorization() {
        binding.notificationAuthorization.setOnCheckedChangeListener { _, _ ->
            checkSaveButtonAvailability()
        }

        binding.notificationNewProducts.setOnCheckedChangeListener { _, _ ->
            checkSaveButtonAvailability()
        }
    }

    private fun checkSaveButtonAvailability() {
        binding.saveButton.isEnabled =
            checkInputName() &&
            !binding.editPhoneText.text.isNullOrEmpty() &&
            checkRadioButton() &&
            checkNotifications()
        if (binding.saveButton.isEnabled) pressButtonSave()
    }

    private fun pressButtonSave() {
        binding.saveButton.setOnClickListener {
            Toast.makeText(this, resources.getText(R.string.toast_save_text), Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}