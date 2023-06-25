package com.rogergcc.filmsthemoviedbapp.domain.usecase


import com.rogergcc.filmsthemoviedbapp.data.model.MovieList
import com.rogergcc.filmsthemoviedbapp.domain.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val loginRepository: MovieRepository) {
    suspend operator fun invoke(): MovieList {
        return loginRepository.getPopularMovies()
    }


}