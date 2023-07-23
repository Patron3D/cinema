package my.project.cinema.json.random

data class RandomList(
    val kinopoiskId: Int,
    val imdbId: String,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val genres: List<Genre>,
    val ratingKinopoisk: Number?,
    val year: Number?,
    val type: FILMEnum?,
    val posterUrl: String?,
    val posterUrlPreview: String?
)
