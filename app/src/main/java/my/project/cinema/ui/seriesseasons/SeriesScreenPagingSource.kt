package my.project.cinema.ui.seriesseasons

import androidx.paging.PagingSource
import androidx.paging.PagingState
import my.project.cinema.json.screens.ScreenImage
import my.project.cinema.repository.MovieRepository
import retrofit2.HttpException
import java.io.IOException

class SeriesScreenPagingSource(private val id: Int) : PagingSource<Int, ScreenImage>() {
    private val repository = MovieRepository()
    override fun getRefreshKey(state: PagingState<Int, ScreenImage>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ScreenImage> {

        try {
            val page = params.key ?: FIRST_PAGE
            return kotlin.runCatching {
                repository.getImageScreen(id, page, TYPE)
            }.fold(
                onSuccess = {
                    LoadResult.Page(
                        data = it,
                        prevKey = null,
                        nextKey = null
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
        const val TYPE = "STILL"
    }

}