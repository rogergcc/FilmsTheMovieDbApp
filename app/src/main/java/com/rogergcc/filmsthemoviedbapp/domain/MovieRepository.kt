package com.rogergcc.filmsthemoviedbapp.domain



import com.rogergcc.filmsthemoviedbapp.data.model.MovieList

interface MovieRepository {
    suspend fun getUpcomingMovies(): MovieList
    suspend fun getTopRatedMovies(): MovieList
    suspend fun getPopularMovies(): MovieList
}