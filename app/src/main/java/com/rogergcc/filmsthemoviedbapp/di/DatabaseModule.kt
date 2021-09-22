package com.rogergcc.filmsthemoviedbapp.di

import android.content.Context
import androidx.room.Room
import com.rogergcc.filmsthemoviedbapp.data.local.AppDatabase
import com.rogergcc.filmsthemoviedbapp.data.local.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "movie_table"
        ).fallbackToDestructiveMigration()
            .build()
    }
    @Provides
    @Singleton
    fun provideMoviesDao(usersDatabase: AppDatabase): MovieDao {
        return usersDatabase.movieDao()
    }

}