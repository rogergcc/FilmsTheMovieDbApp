package com.rogergcc.filmsthemoviedbapp.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rogergcc.filmsthemoviedbapp.core.BaseViewHolder
import com.rogergcc.filmsthemoviedbapp.core.RowDiffUtil
import com.rogergcc.filmsthemoviedbapp.data.model.Movie
import com.rogergcc.filmsthemoviedbapp.databinding.MovieItemBinding

class MoviesAdapter(
//    private val itemClickListener: OnMovieClickListener,
    val movieDetailsAction: (movie: Movie) -> Unit,
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    private var mMoviesList = emptyList<Movie>()


    fun setData(newData: List<Movie>) {
        val recipesDiffUtil =
            RowDiffUtil(mMoviesList, newData)
        val diffUtilResult = DiffUtil.calculateDiff(recipesDiffUtil)
        mMoviesList = newData
        diffUtilResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UpcomingMoviesViewHolder(itemBinding, parent.context)
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is UpcomingMoviesViewHolder -> holder.bind(mMoviesList[position])
        }
    }

    //    override fun getItemCount(): Int = mItemsMovie.size
    override fun getItemCount(): Int = mMoviesList.size

    private inner class UpcomingMoviesViewHolder(
        val binding: MovieItemBinding,
        val context: Context,
    ) : BaseViewHolder<Movie>(binding.root) {
        override fun bind(item: Movie) {

            binding.apply {
                tvTitleMovie.text = item.title
                tvDescription.text = item.overview

                Glide.with(context).load("https://image.tmdb.org/t/p/w500/${item.poster_path}")
                    .centerCrop().into(imvImagePoster)
            }
        }
    }
}