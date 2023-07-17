package com.rogergcc.filmsthemoviedbapp.data.remote

import com.rogergcc.filmsthemoviedbapp.data.remote.model.MovieListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopulardMovies(@Query("api_key") apiKey: String): MovieListResponse
}

//object RetrofitClient {
//
//    val webservice by lazy {
//        Retrofit.Builder()
//            .baseUrl(AppConstants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build().create(ApiService::class.java)
//    }
//
//}