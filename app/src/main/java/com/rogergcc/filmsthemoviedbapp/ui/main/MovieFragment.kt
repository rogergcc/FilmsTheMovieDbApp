package com.rogergcc.filmsthemoviedbapp.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rogergcc.filmsthemoviedbapp.R
import com.rogergcc.filmsthemoviedbapp.core.Resource
import com.rogergcc.filmsthemoviedbapp.databinding.FragmentMovieBinding
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieUiModel
import com.rogergcc.filmsthemoviedbapp.ui.main.adapters.MoviesAdapter
import com.rogergcc.filmsthemoviedbapp.ui.presentation.MovieViewModel
import com.rogergcc.filmsthemoviedbapp.ui.utils.hide
import com.rogergcc.filmsthemoviedbapp.ui.utils.show
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie)
//    ,MoviesAdapter.OnMovieClickListener {
{
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

//    private lateinit var binding: FragmentMovieBinding

//    private val viewModel by viewModels<MovieViewModel> {
//        MovieViewModelFactory(
//            IMovieRepositoryImpl(
//                RemoteMovieDataSource(RetrofitClient.webservice),
//                LocalMovieDataSource(AppDatabase.getDatabase(requireContext()).movieDao())
//            )
//        )
//    }

    //    private val viewModel by viewModels<MovieViewModel>()
    private val viewModel: MovieViewModel by viewModels()
    private val mAdapterMoviesList by lazy {
        MoviesAdapter() { movie ->
            goToMovieDetailsView(movie)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    //    private val viewModel by activityViewModels<MovieViewModel>()
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {

        _binding = FragmentMovieBinding.inflate(inflater, container, false)

        return binding.root

    }

    private var isMoviesFetched = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentMovieBinding.bind(view)

        binding.rvMovies.apply {
            setHasFixedSize(true)

            layoutManager = LinearLayoutManager(context)
            adapter = mAdapterMoviesList
        }
        if (!isMoviesFetched) {
            observePopularMoviesList()
            isMoviesFetched = true
        }
        binding.rvMovies.scheduleLayoutAnimation()

//        val viewModel:MovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)


    }

    private fun observePopularMoviesList() {

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.show()
                }
                is Resource.Success -> {
                    binding.progressBar.hide()

                    //                    binding.rvMovies.adapter = concatAdapter
                    mAdapterMoviesList.mItemsMovie = result.data.results
                    //                    displayData(result.data.results)
                }
                is Resource.Failure -> {
                    binding.progressBar.show()
                    Log.e("FetchError", "Error: ${result.exception} ")
                    Toast.makeText(
                        requireContext(),
                        "Error: ${result.exception}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }
    }

    private fun goToMovieDetailsView(movieResponse: MovieUiModel) {
        Log.d(TAG, "prevention $movieResponse")
//        requireContext().toast(prevention.toString())


        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movieResponse.posterPath,
            movieResponse.backdropImageUrl!!,
            movieResponse.voteAverage.toFloat(),
            movieResponse.voteCount,
            movieResponse.overview,
            movieResponse.title,
            movieResponse.originalLanguage,
            movieResponse.releaseDate
        )
        findNavController().navigate(action)
    }


//    override fun onMovieClick(movie: MovieResponse) {
//        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
//            movie.poster_path,
//            movie.backdrop_path!!,
//            movie.vote_average.toFloat(),
//            movie.vote_count,
//            movie.overview,
//            movie.title,
//            movie.original_language,
//            movie.release_date
//        )
//        findNavController().navigate(action)
//    }

    companion object {
        private const val TAG = "MovieFragment"
    }
}