package com.example.camerashot.data

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Database(entities = [Photo::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {
    abstract fun photoDao(): PhotoDao
}

@Dao
interface PhotoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun savePhoto(photo: Photo)

    @Query("SELECT * FROM photos")
    fun getPhotos(): Flow<List<Photo>>
}

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey
    @ColumnInfo(name = "photoUri")
    val photoUri: String,
    @ColumnInfo(name = "date")
    val date: String
)