package com.project.falconic_solutions.betsample.data.model.response

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.project.falconic_solutions.betsample.data.model.CartModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    @SerializedName("id") val id: String,
    @SerializedName("sport_key") val sportKey: String,
    @SerializedName("sport_title") val sportTitle: String,
    @SerializedName("commence_time") val commenceTime: String,
    @SerializedName("home_team") val homeTeam: String,
    @SerializedName("away_team") val awayTeam: String,
    @SerializedName("bookmakers") val bookmakers: List<Bookmaker>
): Parcelable

@Parcelize
data class Bookmaker(
    @SerializedName("key") val key: String,
    @SerializedName("title") val title: String,
    @SerializedName("last_update") val lastUpdate: String,
    @SerializedName("markets") val markets: List<Market>
): Parcelable

@Parcelize
data class Market(
    @SerializedName("key") val key: String,
    @SerializedName("last_update") val lastUpdate: String,
    @SerializedName("outcomes") val outcomes: List<Outcome>
): Parcelable

@Parcelize
data class Outcome(
    @SerializedName("name") val name: String, @SerializedName("price") val price: Double
): Parcelable

fun Event.toCartModel(bookmakerKey: String, outcome: Outcome): CartModel {
    val key = "${this.id}_${bookmakerKey}_${outcome.name}"
    return CartModel(
        id = key, homeTeam = this.homeTeam, awayTeam = this.awayTeam, outcome = outcome
    )
}