package my.project.cinema.ui.random

import androidx.paging.PagingSource
import androidx.paging.PagingState
import my.project.cinema.json.random.FILMEnum
import my.project.cinema.json.random.RandomList
import my.project.cinema.repository.MovieRepository
import my.project.cinema.utilit.Variables
import retrofit2.HttpException
import java.io.IOException

class RandomPagingSource : PagingSource<Int, RandomList>() {
    private val repository = MovieRepository()
    override fun getRefreshKey(state: PagingState<Int, RandomList>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RandomList> {

        return try {
            val page = params.key ?: FIRST_PAGE
            kotlin.runCatching {
                repository.getRandomFilms(
                    countries = Variables.randomCountry,
                    genres = Variables.randomGenre,
                    "${FILMEnum.ALL}",
                    ratingFrom = 8,
                    ratingTo = 10,
                    yearFrom = 1000,
                    yearTo = 3000,
                    page
                )
            }.fold(
                onSuccess = {
                    LoadResult.Page(
                        data = it,
                        prevKey = null,
                        nextKey = if (it.isEmpty()) null else page + 1
                    )
                },
                onFailure = { LoadResult.Error(it) }
            )

        } catch (e: HttpException) {
            return LoadResult.Error(e)
        } catch (e: IOException) {
            return LoadResult.Error(e)
        }
    }

    private companion object {
        const val FIRST_PAGE = 1
    }

}