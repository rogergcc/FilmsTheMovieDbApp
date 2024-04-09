package com.rogergcc.filmsthemoviedbapp.core

import com.rogergcc.filmsthemoviedbapp.data.AppError
import com.rogergcc.filmsthemoviedbapp.presentation.utils.ErrorType

sealed class Resource<out T> {
    class Loading<out T> : Resource<T>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val exception: AppError, val errorType: ErrorType) : Resource<Nothing>()

}