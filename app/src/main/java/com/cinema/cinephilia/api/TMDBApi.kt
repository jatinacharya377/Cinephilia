package com.cinema.cinephilia.api

import com.cinema.cinephilia.model.MovieDetails
import com.cinema.cinephilia.model.popular.PopularMovieResults
import com.cinema.cinephilia.model.trending.TrendingMovieResults

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TMDBApi {

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") id: String,
        @Query("api_key") api_key: String
    ): MovieDetails

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") api_key: String
    ): PopularMovieResults

    @GET("movie/now_playing")
    suspend fun getTrendingMovies(
        @Query("api_key") api_key: String,
        @Query("page") page_no: Int
    ): TrendingMovieResults
}