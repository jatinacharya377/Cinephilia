package com.cinema.cinephilia.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.cinema.cinephilia.api.TMDBApi
import com.cinema.cinephilia.api.TMDBService
import com.cinema.cinephilia.model.MovieDetails
import com.cinema.cinephilia.model.popular.PopularMovieResults
import com.cinema.cinephilia.model.trending.TrendingMovieResults
import com.cinema.cinephilia.paging.TMDBPagingSource

class TMDBRepository {

    private var tmdbService: TMDBService = TMDBService()
    private var tmdbApi: TMDBApi = tmdbService.getTMDBApi()

    fun getTrendingMovies(api_key: String) =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                maxSize = 500,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { TMDBPagingSource(tmdbApi, api_key) }
        ).liveData

    suspend fun getMovieDetails(id: String, api_key: String): MovieDetails {

        return tmdbApi.getMovieDetails(id, api_key)
    }

    suspend fun getPopularMovies(api_key: String): PopularMovieResults {

        return tmdbApi.getPopularMovies(api_key)
    }
}