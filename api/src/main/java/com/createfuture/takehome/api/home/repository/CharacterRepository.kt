package com.createfuture.takehome.api.home.repository

import kotlinx.coroutines.flow.StateFlow
import com.createfuture.takehome.api.common.Result
import com.createfuture.takehome.model.GotCharacter

interface CharacterRepository {
    val characters: StateFlow<Result<List<GotCharacter>>>

    suspend fun fetchCharacters(
        refresh: Boolean,
    ): Result<List<GotCharacter>>
}