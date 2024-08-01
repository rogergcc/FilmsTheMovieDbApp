package com.rogergcc.filmsthemoviedbapp.domain.usecase


import com.rogergcc.filmsthemoviedbapp.data.NetworkResult
import com.rogergcc.filmsthemoviedbapp.domain.IMovieRepository
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val moviesRepository: IMovieRepository,
) {
    suspend fun popularMovies(): NetworkResult<MovieList> {
        return moviesRepository.getPopularMovies()
    }

    fun moviesByCollection(): Flow<NetworkResult<MovieList>> {
        return moviesRepository.getMoviesByCollection()
    }
}