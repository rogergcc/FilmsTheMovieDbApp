package com.rogergcc.filmsthemoviedbapp.domain



import com.rogergcc.filmsthemoviedbapp.data.model.MovieList

interface MovieRepository {

    suspend fun getPopularMovies(): MovieList
}
