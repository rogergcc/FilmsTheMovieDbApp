package com.rogergcc.filmsthemoviedbapp.domain

import com.rogergcc.filmsthemoviedbapp.data.model.MovieList
import com.rogergcc.filmsthemoviedbapp.domain.utils.DomainMoviesGenerator
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Created on September.
 * year 2021 .
 */
class MovieRepositoryImplTest() {

    @Test
    fun getPopularMovies() {
        return DomainMoviesGenerator.getMovies()
    }

    @Test
    fun verifyMoviesWhenRepoMockReturnList() {
        runBlocking {
            val result = Result.success(DomainMoviesGenerator.getMovies())
//            given(mockBeersRepository.getAllBeers()).willReturn(result)


        }
    }

}