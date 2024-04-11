package com.rogergcc.filmsthemoviedbapp.presentation.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.filmsthemoviedbapp.core.DispatchersProvider
import com.rogergcc.filmsthemoviedbapp.core.Resource
import com.rogergcc.filmsthemoviedbapp.data.AppError
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList
import com.rogergcc.filmsthemoviedbapp.domain.usecase.MoviesUseCase
import com.rogergcc.filmsthemoviedbapp.presentation.utils.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    private val dispatcherProvider: DispatchersProvider,
) :
    ViewModel() {

    private val _movieState = MutableLiveData<Resource<MovieList>>()
    val movieState: LiveData<Resource<MovieList>> get() = _movieState

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch(viewModelScope.coroutineContext + dispatcherProvider.io) {
            _movieState.postValue(Resource.Loading())

            try {
                _movieState.postValue(Resource.Success(moviesUseCase.moviesByCollection()))
            } catch (e: AppError) {
                Log.e("AppLogger", "MovieViewModel AppError: ${e.message} ")
                val errorType = when (e) {
                    is AppError.NetworkError -> ErrorType.NETWORK_ERROR
                    is AppError.ApiError -> ErrorType.API_ERROR
                    is AppError.UnknownError -> ErrorType.UNKNOWN_ERROR
                }
//                val errorMessage = errorType.messageResId
//                val errorTitle = errorType.titleResId
//                val errorImage = errorType.imageResId

                _movieState.postValue(Resource.Failure(e, errorType))
            }
        }

    }
}

//difference of liveData(Dispatchers.IO) { y viewModelScope.launch { }
