package com.rogergcc.filmsthemoviedbapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.rogergcc.filmsthemoviedbapp.core.Resource
import com.rogergcc.filmsthemoviedbapp.domain.usecase.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repo: GetMoviesUseCase
    )
    : ViewModel() {

    fun fetchMainScreenMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success((repo.invoke())))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

//class MovieViewModelFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
//    }
//}