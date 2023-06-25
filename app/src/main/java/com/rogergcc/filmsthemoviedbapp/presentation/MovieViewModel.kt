package com.rogergcc.filmsthemoviedbapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.rogergcc.filmsthemoviedbapp.core.Resource
import com.rogergcc.filmsthemoviedbapp.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
)
    : ViewModel() {

    fun fetchMainScreenMovies() = liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(getMoviesUseCase()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}
//difference of liveData(Dispatchers.IO) { y viewModelScope.launch { }

//class MovieViewModelFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
//    }
//}