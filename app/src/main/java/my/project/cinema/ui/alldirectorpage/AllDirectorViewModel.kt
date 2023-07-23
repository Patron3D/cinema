package my.project.cinema.ui.alldirectorpage

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
class AllDirectorViewModel @Inject constructor(private val repository: MovieRepository) : ViewModel() {

    private val _responseDirector = MutableLiveData<List<StaffActorItem>>()
    val responseDirector = _responseDirector.asFlow()

    fun getFilmDirector(id: Int) = viewModelScope.launch {
        try {
            _responseDirector.postValue(repository.getStaffActor(id))
        } catch (e: Exception) {
            Log.d("getFilmActor", "Страница не найдена")
        }
    }

}