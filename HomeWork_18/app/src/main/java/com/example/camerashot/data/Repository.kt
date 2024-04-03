package com.example.camerashot.data

import android.content.Context
import com.example.camerashot.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Repository(context: Context?) {
    private val photoBase = (context?.applicationContext as App).photoDatabase.photoDao()

    fun savePhoto(uri: String, name: String) {
        val photo = Photo(uri, name)
        CoroutineScope(Dispatchers.IO).launch {
            photoBase.savePhoto(photo)
        }
    }

    fun getPhotos(): Flow<List<Photo>> = photoBase.getPhotos()
}
