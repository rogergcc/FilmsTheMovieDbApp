package com.rogergcc.filmsthemoviedbapp.di

import com.google.gson.GsonBuilder
import com.rogergcc.filmsthemoviedbapp.application.AppConstants
import com.rogergcc.filmsthemoviedbapp.core.DefaultDispatchersProvider
import com.rogergcc.filmsthemoviedbapp.core.DispatchersProvider
import com.rogergcc.filmsthemoviedbapp.data.MovieRepositoryImpl
import com.rogergcc.filmsthemoviedbapp.data.local.LocalMovieDataSource
import com.rogergcc.filmsthemoviedbapp.data.remote.FilmsApiService
import com.rogergcc.filmsthemoviedbapp.data.remote.RemoteMovieDataSource
import com.rogergcc.filmsthemoviedbapp.domain.IMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .client(provideOkHttp())
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()
    private fun provideLoggingInterceptor(): Interceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }
    private fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addNetworkInterceptor(provideLoggingInterceptor())
            .build()
    }
    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): FilmsApiService = retrofit.create(FilmsApiService::class.java)

    @Singleton
    @Provides
    fun provideProductRepository(
        dataSourceRemote: RemoteMovieDataSource,
        dataSourceLocal: LocalMovieDataSource,
    ): IMovieRepository {
        return MovieRepositoryImpl(
            dataSourceRemote, dataSourceLocal
        )
    }

    @Provides
    fun provideDispatcherProvider(): DispatchersProvider {
        return DefaultDispatchersProvider
    }


}

