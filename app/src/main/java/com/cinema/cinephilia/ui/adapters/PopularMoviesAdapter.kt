package com.cinema.cinephilia.ui.adapters

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.cinema.cinephilia.R
import com.cinema.cinephilia.model.popular.PopularMovie
import com.cinema.cinephilia.utils.getProgressDrawable
import com.cinema.cinephilia.utils.loadImage

class PopularMoviesAdapter(
    private val context: Context,
    private val popularMoviesList: List<PopularMovie>
) : RecyclerView.Adapter<PopularMoviesAdapter.PopularMoviesViewHolder>() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {

        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_popular_movies, parent, false)
        return PopularMoviesViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {

        holder.bind(popularMoviesList[position])
    }

    override fun getItemCount(): Int {

        return if (popularMoviesList.size > 3) 3 else popularMoviesList.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    class PopularMoviesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val popularMoviesImage: ImageView = itemView.findViewById(R.id.popularMoviesImage)
        private val progressDrawable = getProgressDrawable(itemView.context)

        fun bind(popularMovie: PopularMovie) {

            val url = "https://image.tmdb.org/t/p/original" + popularMovie.backdropUrl
            popularMoviesImage.loadImage(url, progressDrawable)
        }
    }
}