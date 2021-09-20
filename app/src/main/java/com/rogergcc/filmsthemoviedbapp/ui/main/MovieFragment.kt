package com.rogergcc.filmsthemoviedbapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rogergcc.filmsthemoviedbapp.BuildConfig
import com.rogergcc.filmsthemoviedbapp.R
import com.rogergcc.filmsthemoviedbapp.core.Resource
import com.rogergcc.filmsthemoviedbapp.data.model.Movie
import com.rogergcc.filmsthemoviedbapp.databinding.FragmentMovieBinding
import com.rogergcc.filmsthemoviedbapp.presentation.MovieViewModel
import com.rogergcc.filmsthemoviedbapp.ui.main.adapters.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie), MoviesAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding
//    private val viewModel by viewModels<MovieViewModel> {
//        MovieViewModelFactory(
//            MovieRepositoryImpl(
//                RemoteMovieDataSource(RetrofitClient.webservice),
//                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
//            )
//        )
//    }

    private val viewModel by viewModels<MovieViewModel>()
    private val mAdapterMoviesList by lazy { MoviesAdapter(this) }

//    private val viewModel by activityViewModels<MovieViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        BuildConfig.VERSION_CODE
        binding.rvMovies.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterMoviesList
        }

//        val viewModel:MovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

//                    binding.rvMovies.adapter = concatAdapter
                    mAdapterMoviesList.mItemsMovie = result.data.results
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.e("FetchError", "Error: ${result.exception} ")
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        })
    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path!!,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }
}