package com.rogergcc.filmsthemoviedbapp.ui.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rogergcc.filmsthemoviedbapp.core.Resource
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieList
import com.rogergcc.filmsthemoviedbapp.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(private val getMoviesUseCase: GetMoviesUseCase) :
    ViewModel() {

    private val _movieState = MutableLiveData<Resource<MovieList>>()
    val movieStateResource: LiveData<Resource<MovieList>> get() = _movieState

    init {
        fetchMovies()
    }

    private fun fetchMovies() {
        viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            _movieState.postValue(Resource.Loading())

            try {
                _movieState.postValue(Resource.Success(getMoviesUseCase()))
            } catch (e: Exception) {
                _movieState.postValue(Resource.Failure(e))
            }

        }

    }

}
//difference of liveData(Dispatchers.IO) { y viewModelScope.launch { }
