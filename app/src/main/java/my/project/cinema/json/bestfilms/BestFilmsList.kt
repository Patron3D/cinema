package my.project.cinema.json.bestfilms

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BestFilmsList(
    val filmId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val year: String,
    val countries: List<Country>,
    val genres: List<Genre>,
    val rating: Number?,
    val posterUrl: String,
    val posterUrlPreview: String
) : Parcelable
