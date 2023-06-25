package com.rogergcc.filmsthemoviedbapp.data

import com.rogergcc.filmsthemoviedbapp.core.InternetCheck
import com.rogergcc.filmsthemoviedbapp.data.local.LocalMovieDataSource
import com.rogergcc.filmsthemoviedbapp.data.model.MovieList
import com.rogergcc.filmsthemoviedbapp.data.model.isNull
import com.rogergcc.filmsthemoviedbapp.data.model.toMovieEntity
import com.rogergcc.filmsthemoviedbapp.data.remote.RemoteMovieDataSource
import com.rogergcc.filmsthemoviedbapp.domain.MovieRepository

//@ExperimentalCoroutinesApi
//@ActivityRetainedScoped
//class MovieRepositoryImpl @Inject constructor(
class MovieRepositoryImpl constructor(
    private val dataSourceRemote: RemoteMovieDataSource,
    private val dataSourceLocal: LocalMovieDataSource
) : MovieRepository {


    override suspend fun getPopularMovies(): MovieList {
        return if (InternetCheck.isNetworkAvailable()) {
            dataSourceRemote.getPopularMovies().results.forEach { movie ->
                if (!movie.isNull()) dataSourceLocal.saveMovie(movie.toMovieEntity("popular"))
            }
            dataSourceLocal.getPopularMovies()
        } else {
            dataSourceLocal.getPopularMovies()
        }
    }
}