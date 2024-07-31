package com.rogergcc.filmsthemoviedbapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.filmsthemoviedbapp.core.DispatchersProvider
import com.rogergcc.filmsthemoviedbapp.data.AppError
import com.rogergcc.filmsthemoviedbapp.data.NetworkResult
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList
import com.rogergcc.filmsthemoviedbapp.domain.usecase.MoviesUseCase
import com.rogergcc.filmsthemoviedbapp.presentation.utils.ErrorType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val moviesUseCase: MoviesUseCase,
    private val dispatcherProvider: DispatchersProvider,
) :
    ViewModel() {

    private val _movieState = MutableStateFlow<MoviesUiState>(MoviesUiState.Loading)
    val movieState: StateFlow<MoviesUiState> = _movieState

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch(viewModelScope.coroutineContext + dispatcherProvider.io) {

            _movieState.value = MoviesUiState.Loading
            moviesUseCase.moviesByCollection().collect() { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        _movieState.value = MoviesUiState.Success(result.data)

                    }

                    is NetworkResult.Failure -> {
                        val errorType = when (result.error) {
                            is AppError.NetworkError -> ErrorType.NETWORK_ERROR
                            is AppError.ApiError -> ErrorType.API_ERROR
                            is AppError.UnknownError -> ErrorType.UNKNOWN_ERROR
                            else -> ErrorType.UNKNOWN_ERROR
                        }
                        _movieState.value = MoviesUiState.Failure(result.error, errorType)
                    }
                }
            }

        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val data: MovieList? = null,
        val error: String? = null,
    )

    sealed class MoviesUiState {
//        object Idle : MoviesUiState()
        object Loading : MoviesUiState()
        data class Success(val movies: MovieList) : MoviesUiState()
        data class Failure(val exception: Exception, val errorType: ErrorType) : MoviesUiState()
    }
//difference of liveData(Dispatchers.IO) { y viewModelScope.launch { }
}