package com.rogergcc.filmsthemoviedbapp.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.rogergcc.filmsthemoviedbapp.R
import com.rogergcc.filmsthemoviedbapp.core.Resource
import com.rogergcc.filmsthemoviedbapp.databinding.FragmentMovieBinding
import com.rogergcc.filmsthemoviedbapp.databinding.MovieItem2Binding
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieUiModel
import com.rogergcc.filmsthemoviedbapp.presentation.home.adapters.MoviesAdapter2
import com.rogergcc.filmsthemoviedbapp.presentation.utils.hide
import com.rogergcc.filmsthemoviedbapp.presentation.utils.show
import com.rogergcc.filmsthemoviedbapp.presentation.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()
    private val mAdapterMoviesList by lazy {
        MoviesAdapter2() { movie, binding ->
            goToMovieDetailsView(movie, binding)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)


    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        _binding = FragmentMovieBinding.bind(view)

        binding.rvMovies.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = mAdapterMoviesList
        }

//        launchOnLifecycleScope {
//        }
        observePopularMoviesList()


        binding.rvMovies.scheduleLayoutAnimation()

//        val viewModel: MovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        postponeEnterTransition(300, TimeUnit.MILLISECONDS)
        binding.rvMovies.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun observePopularMoviesList() {
        viewModel.movieState.observe(viewLifecycleOwner) { result ->
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
                    binding.progressBar.hide()
                    binding.errorStateView.root.show()
                    binding.errorStateView.tvErrorStateMessage.text = result.exception.toString()
                    binding.errorStateView.imgStateError.setImageResource(R.drawable.error_image)


                    result.errorType.let {
                        binding.errorStateView.tvErrorStateMessage.text = getString(it.messageResId)
                        binding.errorStateView.imgStateError.setImageResource(it.imageResId)
                    }

                    Log.e("AppLogger", "MovieFragment Error: ${result.exception} ")
                    requireContext().toast("Error: ${result.exception}")
                }
            }
        }
    }

    private fun goToMovieDetailsView(movieSelected: MovieUiModel, binding: MovieItem2Binding) {
        Log.d(TAG, "prevention $movieSelected")
//        requireContext().toast(prevention.toString())

        val extras = FragmentNavigatorExtras(
            binding.imvImagePoster to "avatar_${movieSelected.id}",
            binding.tvTitle to "title_${movieSelected.id}",
//            binding.tvDescription to "description_${movieSelected.id}"
        )

//        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movieResponse)
//        findNavController().navigate(action, extras)

        findNavController().navigate(
            MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(movieSelected),
            extras
        )
    }

    private fun launchOnLifecycleScope(execute: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            execute()
        }
    }


    companion object {
        private const val TAG = "MovieFragment"
    }
}