package com.rogergcc.filmsthemoviedbapp.data.remote

import com.rogergcc.filmsthemoviedbapp.application.AppConstants
import com.rogergcc.filmsthemoviedbapp.application.TimberAppLogger
import com.rogergcc.filmsthemoviedbapp.data.AppError
import com.rogergcc.filmsthemoviedbapp.data.NetworkResult
import com.rogergcc.filmsthemoviedbapp.data.remote.model.CollectionMoviesResponse
import com.rogergcc.filmsthemoviedbapp.data.remote.model.MovieListResponse
import com.rogergcc.filmsthemoviedbapp.presentation.utils.ErrorType
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

//@ExperimentalCoroutinesApi
class RemoteMovieDataSource @Inject constructor(
    private val filmsApiService: FilmsApiService,
) {

    suspend fun getPopularMovies(): MovieListResponse {
        try {
//            throw IOException()
//            throw HttpException(Response.error<MovieListResponse>(404, "Not found".toResponseBody(null)))
//            throw Exception("Simulated exception")

            return filmsApiService.getPopularMovies(AppConstants.API_KEY)


        } catch (e: IOException) {
            TimberAppLogger.e("[$TAG] IOException occurred: ${e.message}")
            throw AppError.NetworkError("Network error occurred")
        } catch (e: HttpException) {
            TimberAppLogger.e("[$TAG] HttpException occurred: ${e.message}")
            throw AppError.ApiError("API error occurred")
        } catch (e: Exception) {
            TimberAppLogger.e("[$TAG] Unknown exception occurred: ${e.message}")
            throw AppError.UnknownError("An unknown error occurred")
        }
    }
    suspend fun getMoviesByCollection(): NetworkResult<CollectionMoviesResponse> {
        return try {
            val response = filmsApiService.getListMovies(AppConstants.API_KEY, 1)
            NetworkResult.Success(response)
        } catch (e: IOException) {
            TimberAppLogger.e("[$TAG] IOException occurred: ${e.message}")
            NetworkResult.Failure(AppError.NetworkError("Network error occurred"))
        } catch (e: HttpException) {
            TimberAppLogger.e("[$TAG] HttpException occurred: ${e.message}")
            NetworkResult.Failure(AppError.ApiError("API error occurred"))
        } catch (e: Exception) {
            TimberAppLogger.e("[$TAG] Unknown exception occurred: ${e.message}")
            NetworkResult.Failure(AppError.UnknownError("An unknown error occurred"))
        }
    }

    companion object {
        private const val TAG = "RemoteMovieDataSource"
    }
}