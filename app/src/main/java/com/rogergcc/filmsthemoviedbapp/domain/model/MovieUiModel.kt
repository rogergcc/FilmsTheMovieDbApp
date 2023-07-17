package com.rogergcc.filmsthemoviedbapp.domain.model


/**
 * Created on julio.
 * year 2023 .
 */

data class MovieUiModel(
    val id: Int = -1,
    val originalTitle: String = "",
    val originalLanguage: String = "",
    val overview: String = "",
    val popularity: Double = -1.0,
    val posterPath: String = "",
    val releaseDate: String = "",
    val title: String = "",
    var movieType: String = "",
    val backdropImageUrl: String? = "",
    val voteAverage: Double = -1.0,
    val voteCount: Int = -1,
)

data class MovieList(val results: List<MovieUiModel> = listOf())
