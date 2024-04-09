package com.rogergcc.filmsthemoviedbapp.presentation.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rogergcc.filmsthemoviedbapp.application.AppConstants
import com.rogergcc.filmsthemoviedbapp.core.BaseViewHolder
import com.rogergcc.filmsthemoviedbapp.core.RowDiffUtil
import com.rogergcc.filmsthemoviedbapp.databinding.MovieItem2Binding
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieUiModel
import com.rogergcc.filmsthemoviedbapp.presentation.utils.loadUrlLoading

class MoviesAdapter2(
    val movieDetailsAction: (movieUi: MovieUiModel,
                             itemBinding: MovieItem2Binding) -> Unit,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var mMoviesList = emptyList<MovieUiModel>()

    fun setData(results: List<MovieUiModel>) {
        val recipesDiffUtil =
            RowDiffUtil(mMoviesList, results)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        mMoviesList = results
        diffUtilResult.dispatchUpdatesTo(this)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            MovieItem2Binding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = UpcomingMoviesViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            movieDetailsAction(mMoviesList[position], itemBinding)
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is UpcomingMoviesViewHolder -> holder.bind(mMoviesList[position])
//            is UpcomingMoviesViewHolder -> holder.bind(mMoviesList[position], holder.binding)
        }
    }
    override fun getItemCount(): Int = mMoviesList.size

    private inner class UpcomingMoviesViewHolder(
        val binding: MovieItem2Binding,
        val context: Context,
    ) : BaseViewHolder<MovieUiModel>(binding.root) {
        override fun bind(item: MovieUiModel) {

            binding.tvTitle.text = item.title
//            binding.tvDescription.text = item.overview
            binding.imvImagePoster.loadUrlLoading(context, "${AppConstants.IMAGE_URL}${item.posterPath}")

            ViewCompat.setTransitionName(binding.imvImagePoster, "avatar_${item.id}")
            ViewCompat.setTransitionName(binding.tvTitle, "title_${item.id}")
//            ViewCompat.setTransitionName(binding.tvDescription, "description_${item.id}")
        }


    }
}