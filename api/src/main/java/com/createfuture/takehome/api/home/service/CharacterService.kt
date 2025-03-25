package com.createfuture.takehome.api.home.service

import com.createfuture.takehome.api.home.model.NetworkCharacter
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class CharacterService @Inject constructor(
    private val retrofit: Retrofit,
) {

    private val token = "Bearer 754t!si@glcE2qmOFEcN" // TODO okhttp interceptor for token

    private val characterApi: CharacterApi by lazy {
        retrofit.create(CharacterApi::class.java)
    }

    suspend fun getCharacters(): Response<List<NetworkCharacter>> {
        return characterApi.getCharacters(token)
    }
}