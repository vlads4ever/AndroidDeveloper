package com.example.thingstodo.presentation

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope

import com.example.thingstodo.databinding.FragmentMainBinding
import com.example.thingstodo.di.DaggerAppComponent
import kotlinx.coroutines.launch

private const val USEFUL_ACTIVITY = "usefulActivity"

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var usefulActivity: String? = null
    private val viewModel: MainViewModel by viewModels {
        DaggerAppComponent.create().mainViewModelFactory()
    }

    companion object {
        fun newInstance() = MainFragment()
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

        savedInstanceState?.let {
            usefulActivity = it.getString(USEFUL_ACTIVITY)
        }

        if (usefulActivity != null) {
            binding.usefulActivityTextView.text = usefulActivity
        }

        binding.refreshButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.reloadUsefulActivity()
                viewModel.activityFlow.collect { state ->
                    when (state) {
                        is State.Failure -> binding.usefulActivityTextView.text = state.value
                        is State.Success -> {
                            usefulActivity = state.value
                            binding.usefulActivityTextView.text = usefulActivity
                        }
                        null -> ""
                    }
                }
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (usefulActivity != null) outState.putString(USEFUL_ACTIVITY, usefulActivity)
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}