package com.example.wordsdatabase.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.wordsdatabase.model.Word
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {
    @Query("SELECT * FROM words")
    fun getAll(): List<Word>

    @Query("SELECT * FROM words LIMIT 5")
    fun getFirstFiveWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(word: Word): Long

    @Delete
    fun delete(word: Word)

    @Query("UPDATE words SET repetition = repetition + 1 WHERE value = :wordValue")
    fun update(wordValue: String)

    @Transaction
    fun insertOrUpdate(word: Word) {
        val result = insert(word)
        if (result == -1L) update(word.value)
    }
}
