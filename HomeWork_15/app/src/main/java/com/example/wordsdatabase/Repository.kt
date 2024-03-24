package com.example.wordsdatabase

import android.content.Context

class Repository(context: Context?) {
    val wordsDao = (context?.applicationContext as App).db.wordsDao()
}