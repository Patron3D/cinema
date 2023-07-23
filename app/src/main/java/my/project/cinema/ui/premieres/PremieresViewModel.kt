package my.project.cinema.ui.premieres

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import my.project.cinema.json.premieres.ListItems
import my.project.cinema.repository.MovieRepository
import my.project.cinema.utilit.Variables.getMonth
import my.project.cinema.utilit.Variables.year
import javax.inject.Inject

@HiltViewModel
class PremieresViewModel @Inject constructor(private val repository: MovieRepository) :
    ViewModel() {

    private val _responsePremier = MutableStateFlow<List<ListItems>>(emptyList())
    val responsePremier = _responsePremier.asStateFlow()

    init {
        loadPremieres()
    }

    private fun loadPremieres() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                kotlin.runCatching {
                    repository.getPremier(year, getMonth())
                }.fold(
                    onFailure = { Log.d("MovieViewModel", it.message ?: "") },
                    onSuccess = { _responsePremier.value = it }
                )
            } catch (e: Exception) {
                Log.d("MovieViewModel_Catch", "Unable to get data")
            }
        }
    }

}