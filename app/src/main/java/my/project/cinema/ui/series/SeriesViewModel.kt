package my.project.cinema.ui.series

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import my.project.cinema.json.seriesall.SeriesList
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor() : ViewModel() {

    val seriesAll: Flow<PagingData<SeriesList>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { SeriesPagingSource() }
    ).flow.cachedIn(viewModelScope)

}