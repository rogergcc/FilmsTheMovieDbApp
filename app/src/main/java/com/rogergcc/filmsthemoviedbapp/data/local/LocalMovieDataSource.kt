package com.rogergcc.filmsthemoviedbapp.data.local

import com.rogergcc.filmsthemoviedbapp.data.model.MovieEntity
import com.rogergcc.filmsthemoviedbapp.data.model.MovieList
import com.rogergcc.filmsthemoviedbapp.data.model.toMovieList
import javax.inject.Inject

//@ExperimentalCoroutinesApi
class LocalMovieDataSource @Inject constructor(private val movieDao: MovieDao) {


    suspend fun getPopularMovies(): MovieList {
        return movieDao.getAllMovies().filter { it.movie_type == "popular" }.toMovieList()
    }

    suspend fun saveMovie(movie: MovieEntity) {
        movieDao.saveMovie(movie)
    }
}