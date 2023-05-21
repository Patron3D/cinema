package my.project.cinema.json.bestfilms

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val country: String
) : Parcelable
