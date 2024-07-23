package com.rogergcc.filmsthemoviedbapp.domain



import com.rogergcc.filmsthemoviedbapp.data.NetworkResult
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList

interface IMovieRepository {

    suspend fun getPopularMovies(): NetworkResult<MovieList>
    suspend fun getMoviesByCollection(): NetworkResult<MovieList>

}
