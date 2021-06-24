package com.cinema.cinephilia.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cinema.cinephilia.api.TMDBApi
import com.cinema.cinephilia.model.trending.TrendingMovie
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class TMDBPagingSource(
    private val tmdbApi: TMDBApi,
    private val apiKey: String
) : PagingSource<Int, TrendingMovie> () {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TrendingMovie> {

        val pageNo = params.key ?: STARTING_PAGE_INDEX

        return try {
            val trendingMovieResults = tmdbApi.getTrendingMovies(apiKey, pageNo)
            val trendingMovieList = trendingMovieResults.trendingMoviesList
            LoadResult.Page(
                data = trendingMovieList,
                prevKey = if (pageNo == STARTING_PAGE_INDEX) null else pageNo - 1,
                nextKey = if (trendingMovieList.isEmpty()) null else pageNo + 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, TrendingMovie>): Int? {
        return state.anchorPosition
    }
}