package com.rogergcc.filmsthemoviedbapp.data.remote

import com.rogergcc.filmsthemoviedbapp.data.model.MovieList
import retrofit2.http.GET
import retrofit2.http.Query

interface WebService {
    @GET("movie/popular")
    suspend fun getPopulardMovies(@Query ("api_key") apiKey: String): MovieList
}

//object RetrofitClient {
//
//    val webservice by lazy {
//        Retrofit.Builder()
//            .baseUrl(AppConstants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
//            .build().create(WebService::class.java)
//    }
//
//}