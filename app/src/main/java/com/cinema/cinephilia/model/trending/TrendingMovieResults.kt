package com.cinema.cinephilia.model.trending

import com.google.gson.annotations.SerializedName

data class TrendingMovieResults(
    @SerializedName("results")
    val trendingMoviesList: List<TrendingMovie>
)
