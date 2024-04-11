package com.rogergcc.filmsthemoviedbapp.presentation.utils


/**
 * Created on abril.
 * year 2024 .
 */
class ResultStateUi<T>(
 private val status: Status,
 val data: T?,
 private val message: String?
) {
 enum class Status { FAILURE, LOADING, SUCCESS }

 override fun toString(): String {
  return "ResultStateUi{status=$status, data=$data, message='$message'}"
 }

 companion object {
  fun <T> failure(msg: String, data: T?): ResultStateUi<T?> {
   return ResultStateUi(Status.FAILURE, data, msg)
  }

  fun <T> loading(data: T?): ResultStateUi<T?> {
   return ResultStateUi(Status.LOADING, data, null)
  }

  fun <T> success(data: T?): ResultStateUi<T?> {
   return ResultStateUi(Status.SUCCESS, data, null)
  }
 }
}


