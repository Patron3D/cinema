package my.project.cinema.ui.seriesseasons

import android.util.Log
import androidx.lifecycle.*
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import my.project.cinema.json.actors.StaffActorItem
import my.project.cinema.json.filmpage.Film
import my.project.cinema.json.screens.ScreenImage
import my.project.cinema.json.seasons.Seasons
import my.project.cinema.json.similar.SimilarFilm
import my.project.cinema.repository.MovieRepository
import my.project.cinema.ui.filmpage.ScreenPagingSource
import javax.inject.Inject

@HiltViewModel
class SeriesInfoViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val _responseFilmId = MutableLiveData<Film>()
    val responseFilmId: LiveData<Film> = _responseFilmId

    private val _responseSeries = MutableLiveData<Seasons>()
    val responseSeries: LiveData<Seasons> = _responseSeries

    private val _responseActor = MutableLiveData<List<StaffActorItem>>()
    val responseActor = _responseActor.asFlow()

    private val _responseSimilar = MutableLiveData<List<SimilarFilm>>()
    val responseSimilar = _responseSimilar.asFlow()

    fun getFilmDetails(id: String) = viewModelScope.launch {
        _responseFilmId.postValue(repository.getFilmsId(id))
    }

    fun getSeriesInfo(id: Int) = viewModelScope.launch {
        _responseSeries.postValue(repository.getSeason(id))
    }

    fun getFilmActor(id: Int) = viewModelScope.launch {
        try {
            _responseActor.postValue(repository.getStaffActor(id))
        } catch (e: Exception) {
            Log.d("getFilmActor", "Страница не найдена")
        }
    }

    fun screens(id: Int): Flow<PagingData<ScreenImage>> = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { ScreenPagingSource(id) }
    ).flow.cachedIn(viewModelScope)

    fun getSimilarFilm(id: Int) = viewModelScope.launch {
        try {
            _responseSimilar.postValue(repository.getSimilarFilm(id))
        } catch (e: Exception) {
            Log.d("getSimilarFilm", "Страница не найдена")
        }
    }

}