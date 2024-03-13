package com.example.finder.ui.main

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.finder.State

import com.example.finder.databinding.FragmentMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.finderField.doOnTextChanged { text, _, _, _ ->
            viewModel.onEditTextChanged(text?.length!!)
        }

        binding.findButton.setOnClickListener {
            this.view?.hideKeyboard()
            viewModel.onFindButtonClick(binding.finderField.text.toString())
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is State.Finish -> {
                        val searchResult = "По запросу ${state.searchText} ничего не найдено."
                        binding.outputTextview.text = searchResult
                        binding.progressBar.isVisible = false
                        binding.findButton.isEnabled = false
                    }
                    State.Loading -> {
                        binding.finderField.text = null
                        binding.findButton.isEnabled = false
                        binding.progressBar.isVisible = true
                    }
                    State.Waiting -> {
                        binding.findButton.isEnabled = false
                        binding.progressBar.isVisible = false
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.buttonVisibility.collect { buttonIsEnabled ->
                binding.findButton.isEnabled = buttonIsEnabled
            }
        }

        fun View.hideKeyboard() {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(windowToken, 0)
        }
    }
}