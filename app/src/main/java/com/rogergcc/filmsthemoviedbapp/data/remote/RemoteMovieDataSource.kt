package com.rogergcc.filmsthemoviedbapp.data.remote

import android.util.Log
import com.rogergcc.filmsthemoviedbapp.application.AppConstants
import com.rogergcc.filmsthemoviedbapp.data.AppError
import com.rogergcc.filmsthemoviedbapp.data.remote.model.MovieListResponse
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

//@ExperimentalCoroutinesApi
class RemoteMovieDataSource @Inject constructor(
    private val apiService: ApiService,
) {

    suspend fun getPopularMovies(): MovieListResponse {
        try {
//            throw IOException()
            throw HttpException(Response.error<MovieListResponse>(404, "Not found".toResponseBody(null)))
//            throw Exception("Simulated exception")

            return apiService.getPopularMovies(AppConstants.API_KEY)
        } catch (e: IOException) {
            Log.e("AppLogger", "[DataSource] IOException occurred: ${e.message}")
            throw AppError.NetworkError("Network error occurred")
        } catch (e: HttpException) {
            Log.e("AppLogger", "[DataSource] HttpException occurred: ${e.message}")
            throw AppError.ApiError("API error occurred")
        } catch (e: Exception) {
            Log.e("AppLogger", "[DataSource] Unknown exception occurred: ${e.message}")
            throw AppError.UnknownError("An unknown error occurred")
        }
    }
}