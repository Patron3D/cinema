package my.project.cinema.ui.home.homerandom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import my.project.cinema.json.random.RandomList
import javax.inject.Inject

@HiltViewModel
class HomeRandomViewModel @Inject constructor() : ViewModel() {

    val randomsFilms: Flow<PagingData<RandomList>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { HomeRandomPagingSource() }
    ).flow.cachedIn(viewModelScope)

}