package com.rogergcc.filmsthemoviedbapp.data.local

import com.rogergcc.filmsthemoviedbapp.data.remote.model.MovieListResponse
import com.rogergcc.filmsthemoviedbapp.domain.Mappers.toMovieList
import javax.inject.Inject

class LocalMovieDataSource @Inject constructor(private val movieDao: MovieDao) {


    suspend fun getPopularMovies(): MovieListResponse {
        return movieDao.getAllMovies().filter { it.movie_type == "popular" }.toMovieList()
    }

    suspend fun saveMovie(movie: MovieEntity) {
        movieDao.saveMovie(movie)
    }
}