package my.project.cinema.json.actors

data class StaffActorItem(
    val description: String?,
    val nameEn: String?,
    val nameRu: String?,
    val posterUrl: String?,
    val professionKey: EnumKey?,
    val professionText: String?,
    val staffId: Int?
)