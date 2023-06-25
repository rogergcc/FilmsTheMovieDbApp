package com.rogergcc.filmsthemoviedbapp.data.remote

import com.rogergcc.filmsthemoviedbapp.application.AppConstants
import com.rogergcc.filmsthemoviedbapp.data.model.MovieList
import javax.inject.Inject

//@ExperimentalCoroutinesApi
class RemoteMovieDataSource @Inject constructor(private val webService: WebService) {


    suspend fun getPopularMovies(): MovieList {
        return webService.getPopulardMovies(AppConstants.API_KEY)
    }
}