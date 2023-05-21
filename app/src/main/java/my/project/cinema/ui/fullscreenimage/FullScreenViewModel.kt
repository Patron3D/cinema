package my.project.cinema.ui.fullscreenimage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import my.project.cinema.json.screens.ScreenImage
import my.project.cinema.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class FullScreenViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    fun screens(id: Int): Flow<PagingData<ScreenImage>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { FullScreenPagingSource(id) }
    ).flow.cachedIn(viewModelScope)

}