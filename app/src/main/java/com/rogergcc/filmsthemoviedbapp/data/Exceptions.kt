package com.rogergcc.filmsthemoviedbapp.data


/**
 * Created on abril.
 * year 2024 .
 */
sealed class AppError(message: String, cause: Throwable? = null) : Exception(message, cause) {
    class NetworkError(message: String, cause: Throwable? = null) : AppError(message, cause)
    class ApiError(message: String, cause: Throwable? = null) : AppError(message, cause)
    class UnknownError(message: String, cause: Throwable? = null) : AppError(message, cause)
}