package com.rogergcc.filmsthemoviedbapp.ui.presentation

import androidx.lifecycle.*
import com.rogergcc.filmsthemoviedbapp.core.Resource
import com.rogergcc.filmsthemoviedbapp.domain.IMovieRepository
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
//        fetchMainScreenMovies()
        fetchMovies()
    }

//    fun fetchMainScreenMovies() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
//        emit(Resource.Loading())
//        try {
//            emit(Resource.Success(getMoviesUseCase()))
//        } catch (e: Exception) {
//            emit(Resource.Failure(e))
//        }
//    }

    private fun fetchMovies() {
        viewModelScope.launch(viewModelScope.coroutineContext + Dispatchers.IO) {
            _movieState.postValue(Resource.Loading())// Emitimos el estado de carga antes

            try {
                _movieState.postValue(Resource.Success(getMoviesUseCase()))  // Emitimos el resultado obtenido del Repository
            } catch (e: Exception) {
                _movieState.postValue(Resource.Failure(e))
            }

        }

    }

}
//difference of liveData(Dispatchers.IO) { y viewModelScope.launch { }

class MovieViewModelFactory(private val repo: IMovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(IMovieRepository::class.java).newInstance(repo)
    }
}