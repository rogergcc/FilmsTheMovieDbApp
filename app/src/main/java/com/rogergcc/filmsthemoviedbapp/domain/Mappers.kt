package com.rogergcc.filmsthemoviedbapp.domain

import com.rogergcc.filmsthemoviedbapp.data.local.MovieEntity
import com.rogergcc.filmsthemoviedbapp.data.remote.model.MovieListResponse
import com.rogergcc.filmsthemoviedbapp.data.remote.model.MovieResponse
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieUiModel


/**
 * Created on julio.
 * year 2023 .
 */
object Mappers {


    // Room
    fun List<MovieEntity>.toMovieList(): MovieListResponse {
        val resultList = mutableListOf<MovieResponse>()
        this.forEach { movieEntity ->
            resultList.add(movieEntity.toMovie())
        }
        return MovieListResponse(resultList)
    }

    fun List<MovieResponse>.toMovieList(): MovieList {
        val resultList = mutableListOf<MovieUiModel>()
        this.forEach { movieResponse ->
            resultList.add(movieResponse.toMovieDomain())
        }
        return MovieList(resultList)
    }

    fun List<MovieEntity>.toMovieListUi(): MovieList {
        val resultList = mutableListOf<MovieUiModel>()
        this.forEach { movieResponse ->
            resultList.add(movieResponse.toMovieDomain())
        }
        return MovieList(resultList)
    }

    fun MovieResponse.toMovieEntity(movieType: String): MovieEntity = MovieEntity(
        this.adult,
        this.backdrop_path.toString(),
        this.id,
        this.original_title,
        this.original_language,
        this.overview,
        this.popularity,
        this.poster_path,
        this.release_date,
        this.title,
        this.video,
        this.vote_average,
        this.vote_count,
        movie_type = movieType
    )


    private fun MovieEntity.toMovie(): MovieResponse = MovieResponse(
        this.adult,
        this.backdrop_path,
        this.id,
        this.original_title,
        this.original_language,
        this.overview,
        this.popularity,
        this.poster_path,
        this.release_date,
        this.title,
        this.video,
        this.vote_average,
        this.vote_count
    )

    private fun MovieEntity.toMovieDomain(): MovieUiModel = MovieUiModel(

        id = this.id,
        originalTitle = this.original_title,
        originalLanguage = this.original_language,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.poster_path.toString(),
        releaseDate = this.release_date,
        title = this.title,
        movieType = this.movie_type,
        backdropImageUrl = this.backdrop_path,
        voteAverage = this.vote_average,
        voteCount = this.vote_count

    )

    private fun MovieResponse.toMovieDomain(): MovieUiModel = MovieUiModel(
        this.id,
        this.original_title,
        this.original_language,
        this.overview,
        this.popularity,
        this.poster_path.toString(),
        this.release_date,
        this.title,
        this.movie_type,
        this.backdrop_path,
        this.vote_average,
        this.vote_count
    )


    fun MovieResponse.isNull(): Boolean {
        return this.backdrop_path == null
    }

}