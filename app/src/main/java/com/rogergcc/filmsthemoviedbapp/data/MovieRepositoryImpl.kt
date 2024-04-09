package com.rogergcc.filmsthemoviedbapp.data

import android.util.Log
import com.rogergcc.filmsthemoviedbapp.core.InternetCheck
import com.rogergcc.filmsthemoviedbapp.data.local.LocalMovieDataSource
import com.rogergcc.filmsthemoviedbapp.data.remote.RemoteMovieDataSource
import com.rogergcc.filmsthemoviedbapp.domain.IMovieRepository
import com.rogergcc.filmsthemoviedbapp.domain.Mappers.toDomain
import com.rogergcc.filmsthemoviedbapp.domain.Mappers.toEntity
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieUiModel

//@ExperimentalCoroutinesApi
//@ActivityRetainedScoped
//class MovieRepositoryImpl @Inject constructor(
class MovieRepositoryImpl constructor(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource,
) : IMovieRepository {
    override suspend fun getPopularMovies(): MovieList {

        try {
            if (!InternetCheck.isNetworkAvailable()) {
        //            return dataSourceLocal.getPopularMovies().results.toMovieList()
                val characters = dataSourceLocal.getPopularMovies()
                val cacheData = characters.map { it.toDomain() }
                return MovieList(cacheData)
            }

            val characters = dataSourceRemote.getPopularMovies()
            val remoteData = characters.results.mapNotNull { it.toDomain() }
            dataSourceLocal.insertMovies(remoteData.map { it.toEntity("popular") })
            return MovieList(remoteData)

        } catch (e: AppError) {
            Log.e("AppLogger", "[MovieRepositoryImpl] Exception e: ${e.message} ")
            throw e
        }
    }

    private suspend fun getCharactersRemote(): List<MovieUiModel> {
        return try {
            val characters = dataSourceRemote.getPopularMovies()
            characters.results.mapNotNull { it.toDomain() }
        } catch (e: Exception) {
            throw e
            emptyList()
        }
    }

    private suspend fun getCharactersCache(): List<MovieUiModel> {
        return try {
            val characters = dataSourceLocal.getPopularMovies()
            characters.map { it.toDomain() }
        } catch (e: Exception) {
            emptyList()
        }
    }
}