package com.rogergcc.filmsthemoviedbapp.domain.usecase


import com.rogergcc.filmsthemoviedbapp.data.AppError
import com.rogergcc.filmsthemoviedbapp.domain.IMovieRepository
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList
import javax.inject.Inject

class MoviesUseCase @Inject constructor(
    private val moviesRepository: IMovieRepository,
) {
    suspend fun popularMovies(): MovieList {
        try {
            return moviesRepository.getPopularMovies()
        } catch (e: AppError) {
            throw e
        }
    }
    suspend fun moviesByCollection(): MovieList {
        try {
            return moviesRepository.getMoviesByCollection()
        } catch (e: AppError) {
            throw e
        }
    }
}