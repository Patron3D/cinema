package my.project.cinema.json.random

data class RandomQuery(
    val items: List<RandomList>,
    val total: Int,
    val totalPages: Int
)