package my.project.cinema.ui.topten

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
class TopTenViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _responseBestListText = MutableLiveData<List<Films>>()
    val responseBestListText: LiveData<List<Films>> = _responseBestListText

    fun getTopFilms(id: Int) = viewModelScope.launch {
        try {
            _responseBestListText.postValue(repository.getBestList(id))
        } catch (e: Exception) {
            Log.d("getActorId", "Страница не найдена")
        }
    }

}