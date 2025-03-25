package com.createfuture.takehome.model

data class GotCharacter(
    val name: String,
    val gender: String,
    val culture: String,
    val born: String,
    val died: String,
    val aliases: List<String>,
    val tvSeries: String,
    val playedBy: List<String>,
)
