package com.cinema.cinephilia.ui.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.cinema.cinephilia.R
import com.cinema.cinephilia.model.trending.TrendingMovie
import com.cinema.cinephilia.ui.adapters.PopularMoviesAdapter
import com.cinema.cinephilia.ui.adapters.TrendingMoviesAdapter
import com.cinema.cinephilia.utils.HorizontalMarginItemDecoration
import com.cinema.cinephilia.viewmodel.TMDBViewModel


class MainActivity : AppCompatActivity(), TrendingMoviesAdapter.TrendingMovieListener {

    private lateinit var trendingMoviesList: ArrayList<TrendingMovie>
    private lateinit var popularMoviesAdapter: PopularMoviesAdapter
    private lateinit var trendingMoviesRecyclerView: RecyclerView
    private lateinit var trendingMoviesAdapter: TrendingMoviesAdapter
    private lateinit var tmdbViewModel: TMDBViewModel
    private lateinit var popularMoviesViewPager: ViewPager2

    private fun initializeViews() {

        popularMoviesViewPager = findViewById(R.id.popularMoviesViewPager)
        tmdbViewModel = ViewModelProvider(this).get(TMDBViewModel::class.java)
        tmdbViewModel.init(getString(R.string.api_key))
        trendingMoviesList = ArrayList()
        trendingMoviesAdapter = TrendingMoviesAdapter()
        trendingMoviesRecyclerView = findViewById(R.id.trendingMoviesRecyclerView)
        trendingMoviesRecyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            setHasFixedSize(true)
            adapter = trendingMoviesAdapter
        }
        trendingMoviesAdapter.setTrendingMovieListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()
        tmdbViewModel.getPopularMovies().observe(this, { popularMoviesList ->

                popularMoviesAdapter = PopularMoviesAdapter(this, popularMoviesList!!)
                popularMoviesViewPager.adapter = popularMoviesAdapter
                popularMoviesViewPager.offscreenPageLimit = 1
                val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
                val currentItemHorizontalMarginPx =
                    resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
                val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
                val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->

                    page.translationX = -pageTranslationX * position
                    page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
                }
                popularMoviesViewPager.setPageTransformer(pageTransformer)
                val itemDecoration = HorizontalMarginItemDecoration(
                    this,
                    R.dimen.viewpager_current_item_horizontal_margin
                )
                popularMoviesViewPager.addItemDecoration(itemDecoration)
                popularMoviesViewPager.setCurrentItem(1, true)
            })
        tmdbViewModel.getTrendingMovies().observe(this, { trendingMovies ->

            trendingMoviesAdapter.submitData(lifecycle, trendingMovies)
        })
    }

    override fun onMovieClick(trendingMoviesImage: ImageView, id: String) {

        intent = Intent(this, MovieDetailsActivity::class.java)
        intent.putExtra("id", id)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, trendingMoviesImage, ViewCompat.getTransitionName(trendingMoviesImage)!!)
        startActivity(intent, options.toBundle())
    }
}