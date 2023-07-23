package my.project.cinema.ui.season

import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.project.cinema.json.filmpage.Film
import my.project.cinema.json.seasons.Item
import my.project.cinema.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class SeasonViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _responseFilmId = MutableLiveData<Film>()
    val responseFilmId: LiveData<Film> = _responseFilmId

    private val _responseSeasonsNumber = MutableLiveData<List<Item>>()
    val responseSeasonsNumber = _responseSeasonsNumber.asFlow()

    private val _responseEpisode = MutableLiveData<Item>()
    val responseEpisode = _responseEpisode.asFlow()

    fun getFilmDetails(id: String) = viewModelScope.launch {
        _responseFilmId.postValue(repository.getFilmsId(id))
    }

    fun getSeasonsNumber(id: Int) = viewModelScope.launch {
        try {
            _responseSeasonsNumber.postValue(repository.getSeasonsNumber(id))
        } catch (e: Exception) {
            Log.d("getSeasonNumber", "Страница не найдена")
        }
    }

    fun getEpisodeInfo(id: Int, position: Int) = viewModelScope.launch {
        try {
            _responseEpisode.postValue(repository.getEpisodeInfo(id, position))
            Log.d("episode", "$_responseEpisode")
        } catch (e: Exception) {
            Log.d("getEpisodeInfo", "Страница не найдена")
        }
    }

}