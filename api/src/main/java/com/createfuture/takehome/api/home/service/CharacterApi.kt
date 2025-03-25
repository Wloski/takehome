package com.createfuture.takehome.api.home.service

import com.createfuture.takehome.api.home.model.NetworkCharacter
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface CharacterApi {
    @GET("/characters")
    suspend fun getCharacters(@Header("Authorization") token: String): Response<List<NetworkCharacter>>
}