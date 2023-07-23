package my.project.cinema.ui.similarfilms

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import my.project.cinema.json.similar.SimilarFilm
import my.project.cinema.repository.MovieRepository
import javax.inject.Inject

@HiltViewModel
class SimilarFilmViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val _responseSimilar = MutableLiveData<List<SimilarFilm>>()
    val responseSimilar = _responseSimilar.asFlow()

    fun getSimilarFilm(id: Int) = viewModelScope.launch {
        try {
            _responseSimilar.postValue(repository.getSimilarFilm(id))
        } catch (e: Exception) {
            Log.d("getSimilarFilm", "Страница не найдена")
        }
    }

}