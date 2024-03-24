package com.example.wordsdatabase.ui.main

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData

import com.example.wordsdatabase.Repository
import com.example.wordsdatabase.databinding.FragmentMainBinding
import com.example.wordsdatabase.model.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var currentWordsList: List<Word>

    companion object {
        fun newInstance() = MainFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        binding.addButton.setOnClickListener {
            val matching = Regex("[a-zA-Zа-яА-Я-']+").matches(binding.editText.text)
            when (matching) {
                true -> {
                    val word = Word(binding.editText.text.toString(), 1)
                    binding.editText.text.clear()
                    CoroutineScope(Dispatchers.IO).launch {
                        viewModel.insertOrUpdate(word)
                    }
                }
                false -> Toast.makeText(
                    requireContext(),
                    "Неверный формат: допустимы только буквы и тире.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.clearButton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.clearDatabase()
            }
        }

        viewModel.getFirstFiveWords().asLiveData()
            .observe(viewLifecycleOwner) { words ->
                currentWordsList = words
                var wordsInfo = ""
                words.forEach { word ->
                    wordsInfo += "${word.value} (${word.repetition})\n"
                }
                binding.viewWordsList.text = wordsInfo
        }

        return binding.root
    }

    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                    return MainViewModel(Repository(context?.applicationContext)) as T
                } else {
                    throw IllegalArgumentException("")
                }
            }
        }
    }
}