package com.rogergcc.filmsthemoviedbapp.domain



import com.rogergcc.filmsthemoviedbapp.data.NetworkResult
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {

    suspend fun getPopularMovies(): NetworkResult<MovieList>
    fun getMoviesByCollection(): Flow<NetworkResult<MovieList>>

}
