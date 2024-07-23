package com.rogergcc.filmsthemoviedbapp.data

import com.rogergcc.filmsthemoviedbapp.application.TimberAppLogger
import com.rogergcc.filmsthemoviedbapp.core.InternetCheck
import com.rogergcc.filmsthemoviedbapp.data.local.LocalMovieDataSource
import com.rogergcc.filmsthemoviedbapp.data.remote.RemoteMovieDataSource
import com.rogergcc.filmsthemoviedbapp.data.remote.model.CollectionMoviesResponse
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
    override suspend fun getPopularMovies(): NetworkResult<MovieList> {

        try {
            if (!InternetCheck.isNetworkAvailable()) {
        //            return dataSourceLocal.getPopularMovies().results.toMovieList()
                val characters = dataSourceLocal.getPopularMovies()
                val cacheData = characters.map { it.toDomain() }
                return NetworkResult.Success(MovieList(cacheData))
            }

            val characters = dataSourceRemote.getPopularMovies()
            val remoteData = characters.results.mapNotNull { it.toDomain() }
            dataSourceLocal.insertMovies(remoteData.map { it.toEntity("popular") })
            return NetworkResult.Success(MovieList(remoteData))

        } catch (e: AppError) {
//            Log.e("AppLogger", "[MovieRepositoryImpl] Exception e: ${e.message} ")
            TimberAppLogger.e("[$TAG] Exception e: ${e.message} ")
            throw e
        }
    }

    override suspend fun getMoviesByCollection(): NetworkResult<MovieList> {
//        try {
//            if (!InternetCheck.isNetworkAvailable()) {
//                return NetworkResult.Failure(AppError.NetworkError("No internet connection"))
//            }

//            if (!InternetCheck.isNetworkAvailable()) {
//                //            return dataSourceLocal.getPopularMovies().results.toMovieList()
//                val characters = dataSourceLocal.getPopularMovies()
//                val cacheData = characters.map { it.toDomain() }
//                return NetworkResult.Success(MovieList(cacheData))
//            }

            val moviesCollectionResponse = dataSourceRemote.getMoviesByCollection()

             when (moviesCollectionResponse) {
                is NetworkResult.Success -> {
                    val responseData = moviesCollectionResponse.data.movies.map { item ->
                        return@map MovieUiModel(
                            id = item.id,
                            originalTitle = item.originalTitle,
                            originalLanguage = item.originalLanguage,
                            overview = item.overview,
                            popularity = item.popularity,
                            posterPath = item.posterPath,
                            releaseDate = item.releaseDate,
                            title = item.title,
                            movieType = "collection",
                            backdropImageUrl = item.backdropPath,
                            voteAverage = item.voteAverage,
                            voteCount = item.voteCount
                        )
                    }
                    dataSourceLocal.insertMovies(responseData.map { it.toEntity("collection") })
                    return NetworkResult.Success(MovieList(responseData))

                }
                is NetworkResult.Failure -> {
                    return NetworkResult.Failure(moviesCollectionResponse.error)
                }
            }


//        } catch (e: AppError) {
//            TimberAppLogger.e("[$TAG] Exception e: ${e.message} ")
//            return NetworkResult.Failure(e)
//        }

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

    companion object {
        private const val TAG = "MovieRepositoryImpl"
    }
}