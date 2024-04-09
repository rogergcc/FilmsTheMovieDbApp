package com.rogergcc.filmsthemoviedbapp.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.rogergcc.filmsthemoviedbapp.R
import com.rogergcc.filmsthemoviedbapp.application.AppConstants
import com.rogergcc.filmsthemoviedbapp.databinding.FragmentMovieDetailBinding
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieUiModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailFragment : Fragment(R.layout.fragment_movie_detail) {

//    private val args by navArgs<MovieDetailFragmentArgs>()

//    private val args: MovieDetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentMovieDetailBinding

    private lateinit var movieArg: MovieUiModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        sharedElementEnterTransition =
//            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//        postponeEnterTransition()
        val args = requireArguments()
        movieArg = MovieDetailFragmentArgs.fromBundle(args).movie


        //            Fade fade = new Fade();
//            fade.excludeTarget(R.id.appBar, true);
//            fade.excludeTarget(android.R.id.statusBarBackground, true);
//            fade.excludeTarget(android.R.id.navigationBarBackground, true);

        activity?.window?.enterTransition = null
        activity?.window?.exitTransition = null

        //        sharedElementEnterTransition =
//            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
//        postponeEnterTransition(300, TimeUnit.MILLISECONDS)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

//        postponeEnterTransition()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        view.doOnPreDraw { startPostponedEnterTransition() }
        binding = FragmentMovieDetailBinding.bind(view)
//        val movieArg = args.movie
        ViewCompat.setTransitionName(binding.imvImagePoster, "avatar_${movieArg.id}")
        ViewCompat.setTransitionName(binding.tvTitle, "title_${movieArg.id}")
//        ViewCompat.setTransitionName(binding.txtRating, "rating_${movieArg.id}")
//        ViewCompat.setTransitionName(binding.txtReleased, "released_${movieArg.id}")
//        ViewCompat.setTransitionName(binding.txtLanguage, "language_${movieArg.id}")
        ViewCompat.setTransitionName(binding.txtDescription, "description_${movieArg.id}")


        Glide.with(requireContext()).load("${AppConstants.IMAGE_URL}${movieArg.posterPath}")
            .centerCrop().into(binding.imvImagePoster)
        Glide.with(requireContext()).load("${AppConstants.IMAGE_URL}${movieArg.backdropImageUrl}")
            .centerCrop().into(binding.imgBackground)
        binding.txtDescription.text = movieArg.overview
        binding.tvTitle.text = movieArg.title
        binding.tvLanguage.text = "Language ${movieArg.originalLanguage}"
        binding.tvRating.text = "${movieArg.voteAverage} (${movieArg.voteCount} Reviews)"
        binding.tvReleased.text = "Released ${movieArg.releaseDate}"


    }
}