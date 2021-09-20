package com.rogergcc.filmsthemoviedbapp.domain

import com.rogergcc.filmsthemoviedbapp.core.InternetCheck
import com.rogergcc.filmsthemoviedbapp.data.local.LocalMovieDataSource
import com.rogergcc.filmsthemoviedbapp.data.model.MovieList
import com.rogergcc.filmsthemoviedbapp.data.model.isNull
import com.rogergcc.filmsthemoviedbapp.data.model.toMovieEntity
import com.rogergcc.filmsthemoviedbapp.data.remote.RemoteMovieDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

//@ExperimentalCoroutinesApi
//@ActivityRetainedScoped
class MovieRepositoryImpl @Inject constructor(
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