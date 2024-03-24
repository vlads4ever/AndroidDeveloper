package com.example.wordsdatabase.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.wordsdatabase.model.Word

@Database(entities = [Word::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun wordsDao(): WordsDao
}