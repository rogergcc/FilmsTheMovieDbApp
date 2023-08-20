package com.rogergcc.filmsthemoviedbapp.data

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
//class IMovieRepositoryImpl @Inject constructor(
class IMovieRepositoryImpl constructor(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource,
) : IMovieRepository {


    override suspend fun getPopularMovies(): MovieList {
        if (InternetCheck.isNetworkAvailable()) {

            val remoteData = getCharactersRemote()

            dataSourceLocal.insertMovies(remoteData.map { it.toEntity("popular") })
            return MovieList(remoteData)

        } else {
//            return dataSourceLocal.getPopularMovies().results.toMovieList()
            val cacheData = getCharactersCache()
            return MovieList(cacheData)
        }

    }

    private suspend fun getCharactersRemote(): List<MovieUiModel> {
        return try {
            val characters = dataSourceRemote.getPopularMovies()
            characters.results.mapNotNull { it.toDomain() }
        } catch (e: Exception) {
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