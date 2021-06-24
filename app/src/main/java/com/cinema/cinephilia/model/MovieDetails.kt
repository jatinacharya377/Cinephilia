package com.cinema.cinephilia.model

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    @SerializedName("adult")
    val ratedR: Boolean,
    @SerializedName("popularity")
    val popularity: Float?,
    @SerializedName("poster_path")
    val posterUrl: String?,
    @SerializedName("original_title")
    val title: String?,
    @SerializedName("genres")
    val genresList: List<Genres>,
    @SerializedName("overview")
    val overview: String?,
    @SerializedName("release_date")
    val release_date: String?,
    @SerializedName("runtime")
    val runtime: Int?,
    @SerializedName("vote_average")
    val vote_average: Float?,
    @SerializedName("vote_count")
    val vote_count: Int?,
)
