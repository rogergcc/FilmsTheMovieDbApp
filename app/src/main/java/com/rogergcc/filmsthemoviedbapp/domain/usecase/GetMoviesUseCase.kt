package com.rogergcc.filmsthemoviedbapp.domain.usecase


import com.rogergcc.filmsthemoviedbapp.domain.IMovieRepository
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val moviesRepository: IMovieRepository) {
    suspend operator fun invoke(): MovieList {
        return moviesRepository.getPopularMovies()
    }


}