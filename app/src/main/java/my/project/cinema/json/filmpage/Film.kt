package my.project.cinema.json.filmpage

data class Film(
    val countries: List<Country?>?,
    val coverUrl: String?,
    val description: String?,
    val filmLength: Int?,
    val genres: List<Genre?>?,
    val imdbId: String?,
    val kinopoiskId: Int = 0,
    val nameEn: String?,
    val nameOriginal: String?,
    val nameRu: String?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingAgeLimits: String?,
    val ratingKinopoisk: Number?,
    val reviewsCount: Int = 0,
    val serial: Boolean?,
    val shortDescription: String?,
    val type: TypeEnum,
    val webUrl: String,
    val year: Int?
)