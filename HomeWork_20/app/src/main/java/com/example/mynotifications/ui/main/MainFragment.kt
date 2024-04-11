package com.example.mynotifications.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.mynotifications.App
import com.example.mynotifications.MainActivity
import com.example.mynotifications.R

import com.example.mynotifications.databinding.FragmentMainBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.google.firebase.messaging.FirebaseMessaging

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding?  = null
    private val binding get() = _binding!!

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            Toast
                .makeText(requireContext(), "Permission is $isGranted", Toast.LENGTH_SHORT)
                .show()
        }

    companion object {
        fun newInstance() = MainFragment()
        private const val NOTIFICATION_ID = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.trowExceptionButton.setOnClickListener {
            FirebaseCrashlytics.getInstance().log("My test crash for Firebase Crashlytics.")
            Toast
                .makeText(requireContext(), "Crash was imitated.", Toast.LENGTH_SHORT)
                .show()
            try {
                throw Exception("Test exception.")
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                Toast
                    .makeText(requireContext(), "Crash in Try-Catch was imitated.", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.getNotificationButton.setOnClickListener {
            createNotification()
        }

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Log.d("TOKEN", it.result)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED) {
                Toast
                    .makeText(requireContext(), "Permission is granted", Toast.LENGTH_SHORT)
                    .show()
            } else {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun createNotification() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent
                .getActivity(requireContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE)
        } else {
            PendingIntent
                .getActivity(requireContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        val notification = NotificationCompat.Builder(requireContext(), App.MY_NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.my_notofication_icon)
            .setContentTitle("My own notification")
            .setContentText("Behold of my new notification!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID, notification)
    }
}