package my.project.cinema.ui.filmography

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.project.cinema.json.actor.Actor
import my.project.cinema.json.actor.Films
import my.project.cinema.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class FilmographyViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _responseActor = MutableLiveData<Actor>()
    val responseActor: LiveData<Actor> = _responseActor

    private val _responseFilmsList = MutableLiveData<List<Films>>()
    val responseFilmsList: LiveData<List<Films>> = _responseFilmsList

    fun getActor(id: Int) = viewModelScope.launch {
        try {
            repository.getActor(id).let {
                if (it.isSuccessful){
                    _responseActor.postValue(it.body())
                } else {
                    Log.d("Tag", "Error Response: ${it.message()}")
                }
            }
        } catch (e: Exception) {
            Log.d("getActorId", "Страница не найдена")
        }
    }

    fun getActorFilms(id: Int) = viewModelScope.launch {
        try {
            _responseFilmsList.postValue(repository.getBestList(id))
        } catch (e: Exception) {
            Log.d("getActorId", "Страница не найдена")
        }
    }

}