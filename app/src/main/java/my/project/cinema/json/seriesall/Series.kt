package my.project.cinema.json.seriesall

data class Series(
    val items: List<SeriesList>,
    val total: Int,
    val totalPages: Int
)