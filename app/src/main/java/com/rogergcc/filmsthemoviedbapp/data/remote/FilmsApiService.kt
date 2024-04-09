package com.rogergcc.filmsthemoviedbapp.data.remote

import com.rogergcc.filmsthemoviedbapp.data.remote.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException

interface FilmsApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String): MovieListResponse
}

class FakeFilmsApiService : FilmsApiService {
    // Simula una excepción de red
    override suspend fun getPopularMovies(apiKey: String): MovieListResponse {
        throw IOException("Network error occurred")
    }
}

//object RetrofitClient {
//
//    val webservice by lazy {
//        Retrofit.Builder()
//            .baseUrl(AppConstants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build().create(FilmsApiService::class.java)
//    }
//
//}