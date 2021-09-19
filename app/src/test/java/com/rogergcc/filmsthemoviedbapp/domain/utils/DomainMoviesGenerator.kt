package com.rogergcc.filmsthemoviedbapp.domain.utils

import com.rogergcc.filmsthemoviedbapp.data.model.MovieEntity


object DomainMoviesGenerator {

    fun getMovies() {
        return
            listOf(
                MovieEntity(
                    adult = false,
                    backdrop_path = "",
                    id = -1,
                    original_title = "",
                    original_language = "",
                    overview = "",
                    popularity = -1.0,
                    poster_path = "",
                    release_date = "",
                    title = "",
                    video = false,
                    vote_average = -1.0,
                    vote_count = -1,
                    movie_type = ""
                ),
            )
    }


}
