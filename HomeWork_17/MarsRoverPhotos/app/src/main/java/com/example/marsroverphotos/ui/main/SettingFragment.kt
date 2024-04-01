package com.example.marsroverphotos.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.marsroverphotos.R
import com.example.marsroverphotos.databinding.FragmentSettingBinding

private const val DATE = "date"

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding?  = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarView.setOnDateChangeListener { _ , year, month, dayOfMonth ->
            val date = "$year-${month + 1}-$dayOfMonth"
            Toast.makeText(context, date, Toast.LENGTH_SHORT).show()
            val bundle = Bundle().apply {
                putString(DATE, date)
            }
            findNavController()
                .navigate(resId = R.id.action_settingFragment_to_mainFragment, args = bundle)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}