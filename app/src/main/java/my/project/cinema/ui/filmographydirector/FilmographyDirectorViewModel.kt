package my.project.cinema.ui.filmographydirector

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.project.cinema.json.actor.Films
import my.project.cinema.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class FilmographyDirectorViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val _responseFilms = MutableLiveData<List<Films>>()
    val responseFilms: LiveData<List<Films>> = _responseFilms

    fun getDirectorFilms(id: Int) = viewModelScope.launch {
        try {
            _responseFilms.postValue(repository.getBestList(id))
        } catch (e: Exception) {
            Log.d("getActorId", "Страница не найдена")
        }
    }

}