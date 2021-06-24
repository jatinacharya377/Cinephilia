package com.cinema.cinephilia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn

import com.cinema.cinephilia.model.MovieDetails
import com.cinema.cinephilia.model.popular.PopularMovie
import com.cinema.cinephilia.model.trending.TrendingMovie
import com.cinema.cinephilia.repository.TMDBRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TMDBViewModel: ViewModel() {

    private lateinit var apiKey: String
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val popularMoviesData = MutableLiveData<List<PopularMovie>>()
    private val movieDetailsData = MutableLiveData<MovieDetails>()
    private var tmdbRepository = TMDBRepository()

    fun getMovieDetails(id: String): LiveData<MovieDetails> {

        coroutineScope.launch {
            val movieDetails = tmdbRepository.getMovieDetails(id, apiKey)
            movieDetailsData.postValue(movieDetails)
        }
        return movieDetailsData
    }

    fun getPopularMovies(): LiveData<List<PopularMovie>> {

        coroutineScope.launch {
            val popularMovieResults = tmdbRepository.getPopularMovies(apiKey)
            popularMoviesData.postValue(popularMovieResults.popularMoviesList)
        }
        return popularMoviesData
    }

    fun getTrendingMovies(): LiveData<PagingData<TrendingMovie>> {

        return tmdbRepository.getTrendingMovies(apiKey).cachedIn(viewModelScope)
    }

    fun init(api_key: String) {

        apiKey = api_key
    }
}