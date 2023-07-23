package my.project.cinema.ui.home.homepopular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import my.project.cinema.json.bestfilms.BestFilmsList
import javax.inject.Inject

@HiltViewModel
class HomePopularViewModel @Inject constructor() : ViewModel() {

    val popularFilms: Flow<PagingData<BestFilmsList>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { HomePopularPagingSource() }
    ).flow.cachedIn(viewModelScope)

}