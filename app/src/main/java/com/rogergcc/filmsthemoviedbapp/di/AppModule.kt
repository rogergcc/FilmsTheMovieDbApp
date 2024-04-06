package com.rogergcc.filmsthemoviedbapp.di

import com.google.gson.GsonBuilder
import com.rogergcc.filmsthemoviedbapp.application.AppConstants
import com.rogergcc.filmsthemoviedbapp.core.DefaultDispatchersProvider
import com.rogergcc.filmsthemoviedbapp.core.DispatchersProvider
import com.rogergcc.filmsthemoviedbapp.data.MovieRepositoryImpl
import com.rogergcc.filmsthemoviedbapp.data.local.LocalMovieDataSource
import com.rogergcc.filmsthemoviedbapp.data.remote.ApiService
import com.rogergcc.filmsthemoviedbapp.data.remote.RemoteMovieDataSource
import com.rogergcc.filmsthemoviedbapp.domain.IMovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

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

