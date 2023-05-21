package my.project.cinema.json.bestfilms

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BestFilms(
    val films: List<BestFilmsList>,
    val pagesCount: Int
) : Parcelable