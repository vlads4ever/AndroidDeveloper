package com.example.wordsdatabase

import android.app.Application
import androidx.room.Room
import com.example.wordsdatabase.database.AppDatabase

class App: Application() {
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room.inMemoryDatabaseBuilder(
            applicationContext,
            AppDatabase::class.java,
//            "db"
        ).build()
    }
}