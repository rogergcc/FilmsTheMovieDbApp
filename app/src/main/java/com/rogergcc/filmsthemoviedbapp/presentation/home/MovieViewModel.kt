package com.rogergcc.filmsthemoviedbapp.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.filmsthemoviedbapp.core.DispatchersProvider
import com.rogergcc.filmsthemoviedbapp.data.AppError
import com.rogergcc.filmsthemoviedbapp.data.NetworkResult
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

    private val _movieState = MutableLiveData<MoviesUiState>()
    val movieState: LiveData<MoviesUiState> get() = _movieState

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch(viewModelScope.coroutineContext + dispatcherProvider.io) {

            _movieState.postValue(MoviesUiState.Loading)
            when (val result = moviesUseCase.moviesByCollection()) {
                is NetworkResult.Success -> {
                    _movieState.postValue(MoviesUiState.Success(result.data))
                }

                is NetworkResult.Failure -> {
                    val errorType = when (result.error) {
                        is AppError.NetworkError -> ErrorType.NETWORK_ERROR
                        is AppError.ApiError -> ErrorType.API_ERROR
                        is AppError.UnknownError -> ErrorType.UNKNOWN_ERROR
                        else -> ErrorType.UNKNOWN_ERROR
                    }
                    _movieState.postValue(MoviesUiState.Failure(result.error, errorType))
                }

            }
//            _movieState.postValue(MoviesUiState.Idle)

//            try {
//                val movies = moviesUseCase.moviesByCollection()
//                _movieState.postValue(UiState(data = movies.))
//            } catch (e: AppError) {
//                val errorType = when (e) {
//                    is AppError.NetworkError -> ErrorType.NETWORK_ERROR
//                    is AppError.ApiError -> ErrorType.API_ERROR
//                    is AppError.UnknownError -> ErrorType.UNKNOWN_ERROR
//                }
//                _movieState.postValue(UiState(error = errorType.name))
//            } finally {
//                _movieState.postValue(UiState(isLoading = false))
//            }
        }

    }
}

data class UiState(
    val isLoading: Boolean = false,
    val data: MovieList? = null,
    val error: String? = null,
)

sealed class MoviesUiState {
    object Idle : MoviesUiState()
    object Loading : MoviesUiState()
    data class Success(val user: MovieList) : MoviesUiState()
    data class Failure(val exception: Exception, val errorType: ErrorType) : MoviesUiState()
}
//difference of liveData(Dispatchers.IO) { y viewModelScope.launch { }
