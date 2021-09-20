package com.rogergcc.filmsthemoviedbapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rogergcc.filmsthemoviedbapp.data.model.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}