package com.rogergcc.filmsthemoviedbapp.presentation.utils

import com.rogergcc.filmsthemoviedbapp.R


/**
 * Created on abril.
 * year 2024 .
 */

enum class ErrorType(val titleResId: Int, val messageResId: Int, val imageResId: Int) {
    NETWORK_ERROR(
        R.string.network_error_title,
        R.string.network_error_message,
        R.drawable.error_404
    ),
    API_ERROR(R.string.api_error_title, R.string.api_error_message, R.drawable.error_image),
    UNKNOWN_ERROR(
        R.string.unknown_error_title,
        R.string.unknown_error_message,
        R.drawable.error_unknow
    )
}