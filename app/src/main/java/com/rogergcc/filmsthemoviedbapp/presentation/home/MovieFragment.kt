package com.rogergcc.filmsthemoviedbapp.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionInflater
import com.rogergcc.filmsthemoviedbapp.R
import com.rogergcc.filmsthemoviedbapp.application.TimberAppLogger
import com.rogergcc.filmsthemoviedbapp.databinding.FragmentMovieBinding
import com.rogergcc.filmsthemoviedbapp.databinding.MovieItem2Binding
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieUiModel
import com.rogergcc.filmsthemoviedbapp.presentation.home.adapters.MoviesAdapter2
import com.rogergcc.filmsthemoviedbapp.presentation.utils.ErrorType
import com.rogergcc.filmsthemoviedbapp.presentation.utils.hide
import com.rogergcc.filmsthemoviedbapp.presentation.utils.show
import com.rogergcc.filmsthemoviedbapp.presentation.utils.toast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MovieFragment : Fragment(R.layout.fragment_movie) {
    private var _binding: FragmentMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MovieViewModel by viewModels()
    private val mAdapter by lazy {
        MoviesAdapter2() { movie, binding ->
            goToMovieDetailsView(movie, binding)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementReturnTransition =
            TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move)


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

        TimberAppLogger.d("MovieFragment onViewCreated")
        binding.rvMovies.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
            adapter = mAdapter
        }

//        launchOnLifecycleScope {
//        }

        lifecycleScope.launch {
            viewModel.movieState
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .collect { state ->
                    if (state.isLoading) binding.progressBar.show() else binding.progressBar.hide()

                    when {
                        state.data != null -> {
                            updateUI(state.data.results)
                        }
                        state.error != null -> {
                            TimberAppLogger.e("[$TAG] Error: ${state.error} ")
                            requireContext().toast("Error: ${state.error}")
                            showError(state.error)
                        }
                    }
//                    when (state) {
////                        is MovieViewModel.MoviesUiState.Idle -> {
////                            // Ocultar la barra de progreso y la vista de error
////                            binding.progressBar.hide()
////                            binding.errorStateView.root.hide()
////                        }
//
//                        is MovieViewModel.MoviesUiState.Loading -> {
//                            // Mostrar el estado de carga
//                            binding.progressBar.show()
//                        }
//                        is MovieViewModel.MoviesUiState.Success -> {
//                            // Mostrar la lista de películas
//                            binding.progressBar.hide()
//                            binding.errorStateView.root.hide()
//                            updateUI(state.movies.results)
//                        }
//                        is MovieViewModel.MoviesUiState.Failure -> {
//                            binding.progressBar.hide()
//                            TimberAppLogger.e("[$TAG] Error: ${state.exception} ")
//                            requireContext().toast("Error: ${state.exception}")
//                            showError(state.errorType)
//
//                        }
//                    }
                }
        }
        binding.rvMovies.scheduleLayoutAnimation()

//        val viewModel: MovieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        postponeEnterTransition(300, TimeUnit.MILLISECONDS)
        binding.rvMovies.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }


    private fun showError(errorType: ErrorType) {
        // Mostrar el mensaje de error basado en el tipo de error
        binding.errorStateView.root.show()
        binding.errorStateView.tvErrorStateMessage.text = getString(errorType.messageResId)
        binding.errorStateView.imgStateError.setImageResource(errorType.imageResId)

    }

    private fun updateUI(results: List<MovieUiModel>) {
        mAdapter.submitList(results)

    }

    private fun goToMovieDetailsView(movieSelected: MovieUiModel, binding: MovieItem2Binding) {
//        Log.d(TAG, "prevention $movieSelected")
        TimberAppLogger.d("prevention $movieSelected")
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