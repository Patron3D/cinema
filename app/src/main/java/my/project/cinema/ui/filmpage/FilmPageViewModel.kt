package my.project.cinema.ui.filmpage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
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
import my.project.cinema.json.similar.SimilarFilm
import my.project.cinema.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class FilmPageViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _responseFilmId = MutableLiveData<Film>()
    val responseFilmId: LiveData<Film> = _responseFilmId

    private val _responseActor = MutableLiveData<List<StaffActorItem>>()
    val responseActor = _responseActor.asFlow()

    private val _responseSimilar = MutableLiveData<List<SimilarFilm>>()
    val responseSimilar = _responseSimilar.asFlow()

    fun getFilmDetails(id: String) = viewModelScope.launch {
        _responseFilmId.postValue(repository.getFilmsId(id))
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