package com.cinema.cinephilia.model.popular

import com.google.gson.annotations.SerializedName

data class PopularMovieResults(
    @SerializedName("results")
    val popularMoviesList: List<PopularMovie>
)
