package com.example.thingstodo.presentation

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope

import com.example.thingstodo.databinding.FragmentMainBinding
import com.example.thingstodo.di.DaggerAppComponent
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels {
        DaggerAppComponent.create().mainViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        binding.refreshButton.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.reloadUsefulActivity()
                viewModel.activityFlow.collect { state ->
                    when (state) {
                        is State.Failure -> binding.usefulActivityTextView.text = state.value
                        is State.Success -> binding.usefulActivityTextView.text = state.value
                        null -> ""
                    }
                }
            }
        }

        return binding.root
    }

}