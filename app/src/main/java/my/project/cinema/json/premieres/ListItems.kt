package my.project.cinema.json.premieres

import java.io.Serializable

data class ListItems(
    val genres: List<Genre>,
    val kinopoiskId: Int,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val year: Int
): Serializable
