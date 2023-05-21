package my.project.cinema.ui.allactor

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.project.cinema.json.actors.StaffActorItem
import my.project.cinema.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class AllActorViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _responseActor = MutableLiveData<List<StaffActorItem>>()
    val responseActor = _responseActor.asFlow()

    fun getFilmActor(id: Int) = viewModelScope.launch {
        try {
            _responseActor.postValue(repository.getStaffActor(id))
        } catch (e: Exception) {
            Log.d("getFilmActor", "Страница не найдена")
        }
    }

}