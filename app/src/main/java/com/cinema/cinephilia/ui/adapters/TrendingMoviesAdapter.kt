package com.cinema.cinephilia.ui.adapters

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.cinema.cinephilia.R
import com.cinema.cinephilia.model.trending.TrendingMovie
import com.cinema.cinephilia.utils.getProgressDrawable
import com.cinema.cinephilia.utils.loadImage

class TrendingMoviesAdapter: PagingDataAdapter<TrendingMovie, TrendingMoviesAdapter.TrendingMoviesViewHolder>(MOVIE_COMPARATOR) {

    private lateinit var trendingMovieListener: TrendingMovieListener

    interface TrendingMovieListener {

        fun onMovieClick(trendingMoviesImage: ImageView, id: String)
    }

    fun setTrendingMovieListener(trendingMovieListener: TrendingMovieListener) {

        this.trendingMovieListener = trendingMovieListener
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrendingMoviesViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_trending_movies, parent, false)
        return TrendingMoviesViewHolder(trendingMovieListener, view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: TrendingMoviesViewHolder, position: Int) {

        val currentItem = getItem(position)

        if (currentItem != null) {

            holder.bind(currentItem)

        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    class TrendingMoviesViewHolder(private val trendingMovieListener: TrendingMovieListener, itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val progressDrawable = getProgressDrawable(itemView.context)
        private val trendingMoviesCardView: CardView = itemView.findViewById(R.id.trendingMoviesCardView)
        private val trendingMoviesImage: ImageView = itemView.findViewById(R.id.trendingMoviesImage)

        fun bind(trendingMovie: TrendingMovie) {

            val url = "https://image.tmdb.org/t/p/w500" + trendingMovie.posterUrl
            trendingMoviesImage.loadImage(url, progressDrawable)
            trendingMoviesCardView.setOnClickListener { trendingMovieListener.onMovieClick(trendingMoviesImage, trendingMovie.id!!) }
        }
    }

    companion object {

        private val MOVIE_COMPARATOR = object: DiffUtil.ItemCallback<TrendingMovie>() {
            override fun areItemsTheSame(oldItem: TrendingMovie, newItem: TrendingMovie): Boolean {

                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TrendingMovie,
                newItem: TrendingMovie
            ): Boolean {

                return oldItem == newItem
            }
        }
    }
}