package com.rogergcc.filmsthemoviedbapp.domain.usecase


import com.rogergcc.filmsthemoviedbapp.data.NetworkResult
import com.rogergcc.filmsthemoviedbapp.domain.IMovieRepository
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList
import javax.inject.Inject

class PopularMoviesUseCase @Inject constructor(
    private val moviesRepository: IMovieRepository,
) {
    suspend operator fun invoke(): NetworkResult<MovieList> {
        return moviesRepository.getPopularMovies()
    }
}