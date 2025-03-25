package com.createfuture.takehome.api.home.model

import com.createfuture.takehome.model.GotCharacter

data class NetworkCharacter(
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val aliases: List<String>,
    val tvSeries: List<String>,
    val playedBy: List<String>,
)

fun NetworkCharacter.toCharacter(): GotCharacter {
    val seasonMap = mapOf(
        "Season 1" to "I",
        "Season 2" to "II",
        "Season 3" to "III",
        "Season 4" to "IV",
        "Season 5" to "V",
        "Season 6" to "VI",
        "Season 7" to "VII",
        "Season 8" to "VIII"
    )

    val seasons = this.tvSeries.mapNotNull { seasonMap[it] }.joinToString(", ")

    return GotCharacter(
        name = this.name,
        gender = this.gender,
        culture = this.culture,
        born = this.born,
        died = this.died,
        aliases = this.aliases,
        tvSeries = seasons,
        playedBy = this.playedBy
    )
}