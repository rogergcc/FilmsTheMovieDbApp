package com.rogergcc.filmsthemoviedbapp.data.remote

import com.rogergcc.filmsthemoviedbapp.application.AppConstants
import com.rogergcc.filmsthemoviedbapp.data.remote.model.MovieListResponse
import javax.inject.Inject

//@ExperimentalCoroutinesApi
class RemoteMovieDataSource @Inject constructor(private val apiService: ApiService) {


    suspend fun getPopularMovies(): MovieListResponse {
        return apiService.getPopulardMovies(AppConstants.API_KEY)
    }
}