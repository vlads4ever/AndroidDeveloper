package com.example.finder.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finder.MainViewModelFactory

import com.example.finder.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels { MainViewModelFactory()}
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater,container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

//        binding.finderField.doOnTextChanged { text, _, _, _ ->
//            viewModel.onEditTextChanged(text?.length!!)
//        }

//        binding.findButton.setOnClickListener {
//            this.view?.hideKeyboard()
//            viewModel.onFindButtonClick(binding.finderField.text.toString())
//        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.state.collect { state ->
//                when (state) {
//                    is State.Finish -> {
//                        val searchResult = "По запросу ${state.searchText} ничего не найдено."
//                        binding.outputTextview.text = searchResult
//                        binding.progressBar.isVisible = false
//                        binding.findButton.isEnabled = false
//                    }
//                    State.Loading -> {
//                        binding.finderField.text = null
//                        binding.findButton.isEnabled = false
//                        binding.progressBar.isVisible = true
//                    }
//                    State.Waiting -> {
//                        binding.findButton.isEnabled = false
//                        binding.progressBar.isVisible = false
//                    }
//                }
//            }
//        }

//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.buttonVisibility.collect { buttonIsEnabled ->
//                binding.findButton.isEnabled = buttonIsEnabled
//            }
//        }
    }

//    private fun View.hideKeyboard() {
//        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//        imm.hideSoftInputFromWindow(windowToken, 0)
//    }
}