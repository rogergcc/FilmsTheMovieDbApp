package com.rogergcc.filmsthemoviedbapp.di
import android.content.Context
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.rogergcc.filmsthemoviedbapp.application.AppConstants
import com.rogergcc.filmsthemoviedbapp.data.local.AppDatabase
import com.rogergcc.filmsthemoviedbapp.data.local.LocalMovieDataSource
import com.rogergcc.filmsthemoviedbapp.data.remote.RemoteMovieDataSource
import com.rogergcc.filmsthemoviedbapp.domain.MovieRepository
import com.rogergcc.filmsthemoviedbapp.domain.MovieRepositoryImpl
import com.rogergcc.filmsthemoviedbapp.domain.WebService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRoomInstance(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "movie_table"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideMoviesDao(db: AppDatabase) = db.getMovieDao()

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit = Retrofit.Builder()
        .baseUrl(AppConstants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .build()

    @Singleton
    @Provides
    fun provideWebService(retrofit: Retrofit): WebService = retrofit.create(WebService::class.java)

    @Singleton
    @Provides
    fun provideProductRepository(
        dataSourceRemote: RemoteMovieDataSource,
        dataSourceLocal: LocalMovieDataSource,
    ): MovieRepository {
        return MovieRepositoryImpl(
            dataSourceRemote, dataSourceLocal
        )
    }

//    @Singleton
//    @Provides
//    fun provideLoginRepository(
//        dataSourceRemote: RemoteMovieDataSource,
//        dataSourceLocal: LocalMovieDataSource,
//    ): MovieRepository {
//        return MovieRepositoryImpl(dataSourceRemote, dataSourceLocal)
//    }

}

