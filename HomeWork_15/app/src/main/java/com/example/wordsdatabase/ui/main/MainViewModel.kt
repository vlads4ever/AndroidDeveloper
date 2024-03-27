package com.example.wordsdatabase.ui.main

import androidx.lifecycle.ViewModel
import com.example.wordsdatabase.Repository
import com.example.wordsdatabase.model.Word
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val repository: Repository
) : ViewModel() {
    fun getFirstFiveWords(): Flow<List<Word>> =
        repository.wordsDao.getFirstFiveWords()

    fun clearDatabase() {
        repository.wordsDao.getAll().forEach { word ->
            repository.wordsDao.delete(word)
        }
    }

    fun insertOrUpdate(word: Word) {
        repository.wordsDao.insertOrUpdate(word)
    }
}