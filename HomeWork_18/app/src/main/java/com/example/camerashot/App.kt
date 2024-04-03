package com.example.camerashot

import android.app.Application
import androidx.room.Room
import com.example.camerashot.data.PhotoDatabase

class App : Application() {
    lateinit var photoDatabase: PhotoDatabase

    override fun onCreate() {
        super.onCreate()

        photoDatabase = Room.databaseBuilder(
            applicationContext,
            PhotoDatabase::class.java,
            "photoDataBase"
        ).build()

//        INSTANCE = this
//
//        photoDatabase = Room
//            .inMemoryDatabaseBuilder(
//            this,
//            PhotoDatabase::class.java
//            )
//            .fallbackToDestructiveMigration()
//            .build()
    }

//    companion object {
//        lateinit var INSTANCE: App
//            private set
//    }
}