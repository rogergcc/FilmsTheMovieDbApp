package com.rogergcc.filmsthemoviedbapp.domain



import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList

interface IMovieRepository {

    suspend fun getPopularMovies(): MovieList
    suspend fun getMoviesByCollection(): MovieList

}
