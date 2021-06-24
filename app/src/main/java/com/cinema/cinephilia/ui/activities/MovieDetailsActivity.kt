package com.cinema.cinephilia.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cinema.cinephilia.R
import com.cinema.cinephilia.model.Genres
import com.cinema.cinephilia.ui.adapters.GenreAdapter
import com.cinema.cinephilia.viewmodel.TMDBViewModel

class MovieDetailsActivity : AppCompatActivity() {

    private lateinit var genreList: ArrayList<Genres>
    private lateinit var backButtonCardView: CardView
    private lateinit var bookTicketCardView: CardView
    private lateinit var moviePosterCardView: CardView
    private lateinit var genreAdapter: GenreAdapter
    private lateinit var moviePoster: ImageView
    private lateinit var movieDetailsLayout: LinearLayout
    private lateinit var ratingLayout: LinearLayout
    private lateinit var genreRecycleView: RecyclerView
    private lateinit var id: String
    private lateinit var movieRatingBar: RatingBar
    private lateinit var durationTextView: TextView
    private lateinit var movieNameTextView: TextView
    private lateinit var ratingTextView: TextView
    private lateinit var reviewTextView: TextView
    private lateinit var synopsisTextView: TextView
    private lateinit var synopsisTitleTextView: TextView
    private lateinit var tmdbViewModel: TMDBViewModel

    private fun getDate(releaseDate: String): String {

        val dateArray: List<String> = releaseDate.split("-")
        val year = dateArray[0]
        val month = dateArray[1]
        val day = dateArray[2]
        return day + " " + getMonth(month.toInt()) + ", " + year
    }

    private fun getDuration(runtime: Int): String {

        val hours: Int = runtime / 60
        val minutes: Int = runtime % 60
        return hours.toString() + "hr " + minutes + "min"
    }

    private fun getMonth(month: Int): String {

        var monthName = ""

        when (month) {

            1 -> monthName = "Jan"
            2 -> monthName = "Feb"
            3 -> monthName = "Mar"
            4 -> monthName = "Apr"
            5 -> monthName = "May"
            6 -> monthName = "July"
            7 -> monthName = "June"
            8 -> monthName = "Aug"
            9 -> monthName = "Sept"
            10 -> monthName = "Oct"
            11 -> monthName = "Nov"
            12 -> monthName = "Dec"

        }

        return monthName
    }

    private fun initializeViews() {

        backButtonCardView = findViewById(R.id.backButtonCardView)
        bookTicketCardView = findViewById(R.id.bookTicketCardView)
        durationTextView = findViewById(R.id.durationTextView)
        genreList = ArrayList()
        genreAdapter = GenreAdapter(this, genreList)
        genreRecycleView = findViewById(R.id.genreRecycleView)
        genreRecycleView.apply {
            layoutManager = GridLayoutManager(this@MovieDetailsActivity, 2)
            setHasFixedSize(true)
            adapter = genreAdapter
        }
        id = intent.getStringExtra("id").toString()
        Log.d("id", id)
        movieDetailsLayout = findViewById(R.id.movieDetailsLayout)
        moviePosterCardView = findViewById(R.id.moviePosterCardView)
        tmdbViewModel = ViewModelProvider(this).get(TMDBViewModel::class.java)
        tmdbViewModel.init(getString(R.string.api_key))
        movieNameTextView = findViewById(R.id.movieNameTextView)
        moviePoster = findViewById(R.id.moviePoster)
        movieRatingBar = findViewById(R.id.movieRatingBar)
        ratingLayout = findViewById(R.id.ratingLayout)
        ratingTextView = findViewById(R.id.ratingTextView)
        reviewTextView = findViewById(R.id.reviewTextView)
        synopsisTextView = findViewById(R.id.synopsisTextView)
        synopsisTitleTextView = findViewById(R.id.synopsisTitleTextView)
    }

    private fun setVisibility() {

        bookTicketCardView.visibility = View.VISIBLE
        movieDetailsLayout.visibility = View.VISIBLE
        moviePosterCardView.visibility = View.VISIBLE
        ratingLayout.visibility = View.VISIBLE
        reviewTextView.visibility = View.VISIBLE
        synopsisTextView.visibility = View.VISIBLE
        synopsisTitleTextView.visibility = View.VISIBLE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_details)

        initializeViews()
        backButtonCardView.setOnClickListener { onBackPressed() }
        tmdbViewModel.getMovieDetails(id).observe(this, { movieDetails ->

            genreAdapter.updateDataSet(movieDetails.genresList as ArrayList<Genres>)
            setVisibility()
            val duration: String = if (movieDetails.ratedR) {

                "R | " + movieDetails?.runtime?.let { getDuration(it) } + " | " + movieDetails?.release_date?.let { getDate(it) }

            } else {

                movieDetails?.runtime?.let { getDuration(it) } + " | " + movieDetails?.release_date?.let { getDate(it) }

            }

            durationTextView.text = duration
            val url = "https://image.tmdb.org/t/p/original" + movieDetails?.posterUrl
            Glide.with(this@MovieDetailsActivity).load(url).into(moviePoster)
            movieNameTextView.text = movieDetails?.title
            val rating = movieDetails.vote_average?.div(2)
            movieRatingBar.rating = rating!!
            ratingTextView.text = rating.toString()
            val review = "Reviews: " + movieDetails.vote_count + " (User)"
            reviewTextView.text = review
            synopsisTextView.text = movieDetails.overview
        })
    }
}