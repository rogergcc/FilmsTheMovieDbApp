package com.rogergcc.filmsthemoviedbapp.ui.main.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rogergcc.filmsthemoviedbapp.core.BaseViewHolder
import com.rogergcc.filmsthemoviedbapp.data.model.Movie
import com.rogergcc.filmsthemoviedbapp.databinding.MovieItemBinding

class MoviesAdapter(
        private val itemClickListener: OnMovieClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {
//    private var mItemsMovie = emptyList<Movie>()
    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

//    fun setData(newData: List<Movie>) {
//        mMoviesList = newData
//        notifyDataSetChanged()
//    }

    var mItemsMovie = listOf<Movie>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = UpcomingMoviesViewHolder(itemBinding, parent.context)

        itemBinding.root.setOnClickListener {
            val position = holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onMovieClick(mItemsMovie[position])
        }

        return holder
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is UpcomingMoviesViewHolder -> holder.bind(mItemsMovie[position])
        }
    }

    override fun getItemCount(): Int = mItemsMovie.size

    private inner class UpcomingMoviesViewHolder(
            val binding: MovieItemBinding,
            val context: Context
    ) : BaseViewHolder<Movie>(binding.root) {
        override fun bind(item: Movie) {

            binding.apply {
                tvTitleMovie.text= item.title
                tvDescription.text= item.overview

                Glide.with(context).load("https://image.tmdb.org/t/p/w500/${item.poster_path}").centerCrop().into(imvImagePoster)
            }
        }
    }
}