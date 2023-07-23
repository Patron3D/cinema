package my.project.cinema.api

import my.project.cinema.json.actor.Actor
import my.project.cinema.json.actors.StaffActorItem
import my.project.cinema.json.bestfilms.BestFilms
import my.project.cinema.json.filmpage.Film
import my.project.cinema.json.premieres.Premieres
import my.project.cinema.json.random.RandomQuery
import my.project.cinema.json.screens.Screens
import my.project.cinema.json.seasons.Item
import my.project.cinema.json.seasons.Seasons
import my.project.cinema.json.seriesall.Series
import my.project.cinema.json.similar.Similar
import my.project.cinema.utilit.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/premieres")
    suspend fun premieresList(@Query("year") year: Int, @Query("month") month: String): Premieres

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}")
    suspend fun filmsId(@Path("id") id: String): Film

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/top?type=TOP_250_BEST_FILMS")
    suspend fun topList(@Query("page") page: Int): BestFilms

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/top?type=TOP_100_POPULAR_FILMS")
    suspend fun topPopular(@Query("page") page: Int): BestFilms

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films")
    suspend fun seriesAll(@Query("type") type: String, @Query("page") page: Int): Series

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/seasons")
    suspend fun seriesInfo(@Path("id") id: Int): Seasons

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/seasons")
    suspend fun seasonsNumber(@Path("id") id: Int): Seasons

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/seasons")
    suspend fun seasonsInfo(@Path("id") id: Int): Item

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/seasons")
    suspend fun episodeInfo(@Path("id") id: Int): Seasons

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films")
    suspend fun randomFilms(
        @Query("countries") countries: Int,
        @Query("genres") genres: Int,
        @Query("type") type: String,
        @Query("ratingFrom") ratingFrom: Int,
        @Query("ratingTo") ratingTo: Int,
        @Query("yearFrom") yearFrom: Int,
        @Query("yearTo") yearTo: Int,
        @Query("page") page: Int
    ): RandomQuery

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v1/staff")
    suspend fun staffActor(@Query("filmId") filmId: Int): List<StaffActorItem>

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v1/staff/{id}")
    suspend fun getActor(@Path("id") id: Int): Response<Actor>

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v1/staff/{id}")
    suspend fun getBestList(@Path("id") id: Int): Actor

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/images")
    suspend fun imageScreen(
        @Path("id") id: Int,
        @Query("page") page: Int,
        @Query("type") type: String
    ): Screens

    @Headers("X-API-KEY: $API_KEY")
    @GET("api/v2.2/films/{id}/similars")
    suspend fun similarFilms(@Path("id") id: Int): Similar


}