package my.project.cinema.ui.bestfilms

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
class BestFilmsViewModel @Inject constructor() : ViewModel() {

    val bestFilms: Flow<PagingData<BestFilmsList>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { BestFilmsPagingSource() }
    ).flow.cachedIn(viewModelScope)

}