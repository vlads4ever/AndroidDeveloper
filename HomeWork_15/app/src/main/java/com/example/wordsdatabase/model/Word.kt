package com.example.wordsdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class Word(

    @PrimaryKey
    @ColumnInfo(name = "value")
    val value: String,

    @ColumnInfo(name = "repetition")
    val repetition: Int
)