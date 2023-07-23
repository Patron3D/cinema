package my.project.cinema.repository

import my.project.cinema.di.AppModule.provideRetrofit
import my.project.cinema.utilit.Constants.Companion.BASE_URL
import javax.inject.Inject

class MovieRepository @Inject constructor() {

    suspend fun getPremier(year: Int, month: String) =
        provideRetrofit(BASE_URL).premieresList(year, month).items

    suspend fun getFilmsId(id: String) = provideRetrofit(BASE_URL).filmsId(id)
//    suspend fun getFilmsId(id: String) {
//        try {
//            val response = provideRetrofit(BASE_URL).filmsId(id)
//            if (response.isSuccessful) {
//                response.body()
//            } else {
//                Log.d("ResponseId", "Error data")
//            }
//        } catch (e: Exception) {
//            Log.d("ResponseId", "Error data")
//        }
//    }

    suspend fun getTopList(page: Int) = provideRetrofit(BASE_URL).topList(page).films

    suspend fun getTopPopular(page: Int) = provideRetrofit(BASE_URL).topPopular(page).films

    suspend fun getSeason(id: Int) = provideRetrofit(BASE_URL).seriesInfo(id)

    suspend fun getSeasonsNumber(id: Int) = provideRetrofit(BASE_URL).seasonsNumber(id).items

    suspend fun getEpisodeInfo(id: Int, position: Int) =
        provideRetrofit(BASE_URL).episodeInfo(id).items[position]

    suspend fun getSeriesAll(type: String, page: Int) =
        provideRetrofit(BASE_URL).seriesAll(type, page).items

    suspend fun getRandomFilms(
        countries: Int,
        genres: Int,
        type: String,
        ratingFrom: Int,
        ratingTo: Int,
        yearFrom: Int,
        yearTo: Int,
        page: Int
    ) = provideRetrofit(BASE_URL).randomFilms(
        countries,
        genres,
        type,
        ratingFrom,
        ratingTo,
        yearFrom,
        yearTo,
        page
    ).items

    suspend fun getStaffActor(filmId: Int) = provideRetrofit(BASE_URL).staffActor(filmId)

    suspend fun getBestList(id: Int) = provideRetrofit(BASE_URL).getBestList(id).films

    suspend fun getActor(id: Int) = provideRetrofit(BASE_URL).getActor(id)

    suspend fun getImageScreen(id: Int, page: Int, type: String) =
        provideRetrofit(BASE_URL).imageScreen(id, page, type).items

    suspend fun getSimilarFilm(id: Int) = provideRetrofit(BASE_URL).similarFilms(id).items

}