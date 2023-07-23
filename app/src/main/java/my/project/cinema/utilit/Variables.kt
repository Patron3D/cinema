package my.project.cinema.utilit

import java.text.SimpleDateFormat
import java.util.*

object Variables {
    val year: Int =
        SimpleDateFormat("yyyy", Locale.US).format(System.currentTimeMillis()).toInt()

    fun getMonth(): String {
        return when (SimpleDateFormat("MM", Locale.US).format(System.currentTimeMillis())) {
            "01" -> "JANUARY"
            "02" -> "FEBRUARY"
            "03" -> "MARCH"
            "04" -> "APRIL"
            "05" -> "MAY"
            "06" -> "JUNE"
            "07" -> "JULY"
            "08" -> "AUGUST"
            "09" -> "SEPTEMBER"
            "10" -> "OCTOBER"
            "11" -> "NOVEMBER"
            else -> "DECEMBER"
        }
    }

    val genre = mapOf(
        1 to "Триллер",
        3 to "Криминал",
        5 to "Детектив",
        11 to "Боевик",
        13 to "Комедия"
    )

//    val genre = listOf(1, 3, 9, 33, 34)

    val country = mapOf(
        1 to "США",
        3 to "Франция",
        9 to "Германия",
        33 to "СССР",
        34 to "Россия"
    )

    val randomGenre = genre.keys.random()
    val randomCountry = country.keys.random()

    val titleGenre = randomTitleGenre()
    val titleCountry = randomTitleCountry()

        private fun randomTitleGenre(): String{
        return randomGenre.let {
            when(it){
                1 -> "Триллер"
                3 -> "Криминал"
                5 -> "Детектив"
                11 -> "Боевик"
                else -> "Комедия"
            }
        }
    }

    private fun randomTitleCountry(): String{
        return randomCountry.let {
            when(it){
                1 -> "США"
                3 -> "Франция"
                9 -> "Германия"
                33 -> "СССР"
                else -> "Россия"
            }
        }
    }

}