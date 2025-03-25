package com.createfuture.takehome.api.testdata

import com.createfuture.takehome.api.home.model.NetworkCharacter

val testCharacter1 = NetworkCharacter(
    name = "Jon Snow",
    gender = "Male",
    culture = "Northmen",
    born = "In 283 AC",
    died = "",
    aliases = listOf("Lord Snow", "The White Wolf"),
    tvSeries = listOf("Season 1", "Season 2"),
    playedBy = listOf("Kit Harington")
)

val testCharacter2 = NetworkCharacter(
    name = "Arya Stark",
    gender = "Female",
    culture = "Northmen",
    born = "In 289 AC",
    died = "",
    aliases = listOf("No One", "Cat of the Canals"),
    tvSeries = listOf("Season 1", "Season 2"),
    playedBy = listOf("Maisie Williams")
)

val characterList = listOf(testCharacter1, testCharacter2)