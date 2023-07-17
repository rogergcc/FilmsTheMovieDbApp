package com.rogergcc.filmsthemoviedbapp.data

import com.rogergcc.filmsthemoviedbapp.core.InternetCheck
import com.rogergcc.filmsthemoviedbapp.data.local.LocalMovieDataSource
import com.rogergcc.filmsthemoviedbapp.data.remote.RemoteMovieDataSource
import com.rogergcc.filmsthemoviedbapp.domain.IMovieRepository
import com.rogergcc.filmsthemoviedbapp.domain.Mappers.isNull
import com.rogergcc.filmsthemoviedbapp.domain.Mappers.toMovieEntity
import com.rogergcc.filmsthemoviedbapp.domain.Mappers.toMovieList
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList

//@ExperimentalCoroutinesApi
//@ActivityRetainedScoped
//class IMovieRepositoryImpl @Inject constructor(
class IMovieRepositoryImpl constructor(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource,
) : IMovieRepository {


    override suspend fun getPopularMovies(): MovieList {
        if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getPopularMovies().results.forEach { movie ->
                if (!movie.isNull()) dataSourceLocal.saveMovie(movie.toMovieEntity("popular"))
            }
            return dataSourceRemote.getPopularMovies().results.toMovieList()
        } else {
            return dataSourceLocal.getPopularMovies().results.toMovieList()
        }

    }

}