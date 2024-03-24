package com.example.wordsdatabase

import android.app.Application
import androidx.room.Room
import com.example.wordsdatabase.database.AppDatabase

class App: Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "words.db"
        ).build()
    }
}