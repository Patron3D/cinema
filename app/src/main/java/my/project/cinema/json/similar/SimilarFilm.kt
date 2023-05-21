package my.project.cinema.json.similar

data class SimilarFilm(
    val filmId: Int?,
    val nameRu: String?,
    val nameEn: String?,
    val nameOriginal: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val relationType: SIMILAREnum?
)
