package com.rogergcc.filmsthemoviedbapp.domain.usecase


import com.rogergcc.filmsthemoviedbapp.data.NetworkResult
import com.rogergcc.filmsthemoviedbapp.domain.IMovieRepository
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviesFromCollectionUseCase @Inject constructor(
    private val moviesRepository: IMovieRepository,
) {
    operator fun invoke(): Flow<NetworkResult<MovieList>> {
        return moviesRepository.getMoviesByCollection()
    }
}