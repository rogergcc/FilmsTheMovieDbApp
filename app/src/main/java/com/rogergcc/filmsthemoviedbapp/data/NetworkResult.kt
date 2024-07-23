package com.rogergcc.filmsthemoviedbapp.data


/**
 * Created on julio.
 * year 2024 .
 */
sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val error: Exception) : NetworkResult<T>()
}