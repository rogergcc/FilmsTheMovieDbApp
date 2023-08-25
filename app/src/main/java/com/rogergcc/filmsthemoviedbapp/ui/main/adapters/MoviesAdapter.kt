package com.rogergcc.filmsthemoviedbapp.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rogergcc.filmsthemoviedbapp.application.AppConstants
import com.rogergcc.filmsthemoviedbapp.core.BaseViewHolder
import com.rogergcc.filmsthemoviedbapp.databinding.MovieItemBinding
import com.rogergcc.filmsthemoviedbapp.domain.model.MovieUiModel

class MoviesAdapter(
//    private val itemClickListener: OnMovieClickListener,
    val movieDetailsAction: (movieUi: MovieUiModel, itemBinding: MovieItemBinding) -> Unit,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
    //    private var mItemsMovieResponse = emptyList<MovieResponse>()

    var mItemsMovie = listOf<MovieUiModel>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = UpcomingMoviesViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            movieDetailsAction(mItemsMovie[position], itemBinding)
        }

        return holder
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is UpcomingMoviesViewHolder -> holder.bind(mItemsMovie[position])
//            is UpcomingMoviesViewHolder -> holder.bind(mItemsMovie[position], holder.binding)
        }
    }

    override fun getItemCount(): Int = mItemsMovie.size

    private inner class UpcomingMoviesViewHolder(
        val binding: MovieItemBinding,
        val context: Context,
    ) : BaseViewHolder<MovieUiModel>(binding.root) {
        override fun bind(item: MovieUiModel) {

            binding.apply {
                tvTitle.text = item.title
                tvDescription.text = item.overview

                Glide.with(context).load("${AppConstants.IMAGE_URL}${item.posterPath}")
                    .centerCrop().into(imvImagePoster)
            }

            ViewCompat.setTransitionName(binding.imvImagePoster, "avatar_${item.id}")
            ViewCompat.setTransitionName(binding.tvTitle, "title_${item.id}")
            ViewCompat.setTransitionName(binding.tvDescription, "description_${item.id}")
        }

//        override fun bind(item: MovieUiModel, binding: ViewBinding) {
//            binding as MovieItemBinding
//
//            binding.apply {
//                tvTitleMovie.text = item.title
//                tvDescription.text = item.overview
//
//                Glide.with(context).load("${AppConstants.IMAGE_URL}${item.posterPath}")
//                    .centerCrop().into(imvImagePoster)
//            }
//
//            ViewCompat.setTransitionName(binding.imvImagePoster, "avatar_${item.id}")
//            ViewCompat.setTransitionName(binding.tvTitleMovie, "title_${item.id}")
//            ViewCompat.setTransitionName(binding.tvDescription, "description_${item.id}")
//        }
    }
}