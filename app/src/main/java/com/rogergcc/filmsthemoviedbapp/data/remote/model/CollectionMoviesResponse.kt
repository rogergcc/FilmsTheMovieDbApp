package com.rogergcc.filmsthemoviedbapp.data.remote.model


import com.google.gson.annotations.SerializedName

data class CollectionMoviesResponse(
    @SerializedName("created_by") val createdBy: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("favorite_count") val favoriteCount: Int = 0,
    @SerializedName("id") val id: Int = 0,
    @SerializedName("iso_639_1") val iso6391: String = "",
    @SerializedName("item_count") val itemCount: Int = 0,
    @SerializedName("items") val movies: List<Movie> = listOf(),
    @SerializedName("name") val name: String = "",
    @SerializedName("page") val page: Int = 0,
    @SerializedName("poster_path") val posterPath: Any = Any(),
    @SerializedName("total_pages") val totalPages: Int = 0,
    @SerializedName("total_results") val totalResults: Int = 0
)
data class Movie(
    @SerializedName("adult") val adult: Boolean = false,
    @SerializedName("backdrop_path") val backdropPath: String = "",
    @SerializedName("id") val id: Int = 0,
    @SerializedName("title") val title: String = "",
    @SerializedName("original_language") val originalLanguage: String = "",
    @SerializedName("original_title") val originalTitle: String = "",
    @SerializedName("overview") val overview: String = "",
    @SerializedName("poster_path") val posterPath: String = "",
    @SerializedName("media_type") val mediaType: String = "",
    @SerializedName("runtime") val runtime: Int = 0,
    @SerializedName("revenue") val revenue: Int = 0,
    @SerializedName("genre_ids") val genreIds: List<Int> = listOf(),
    @SerializedName("popularity") val popularity: Double = 0.0,
    @SerializedName("release_date") val releaseDate: String = "",
    @SerializedName("video") val video: Boolean = false,
    @SerializedName("vote_average") val voteAverage: Double = 0.0,
    @SerializedName("vote_count") val voteCount: Int = 0
)