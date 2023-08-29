package com.rogergcc.filmsthemoviedbapp.data.local

import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(private val movieDao: MovieDao) {


    suspend fun getPopularMovies(): List<MovieEntity> {
        return movieDao.getAllMovies().filter { it.movie_type == "popular" }
    }

//    suspend fun saveMovie(movie: MovieEntity) {
//        movieDao.saveMovie(movie)
//    }

    suspend fun insertMovies(movies: List<MovieEntity>) {
        movieDao.insertMovies(movies)
    }
}