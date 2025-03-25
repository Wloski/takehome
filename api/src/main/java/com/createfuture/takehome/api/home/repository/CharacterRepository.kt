package com.createfuture.takehome.api.home.repository

import com.createfuture.takehome.model.GotCharacter
import kotlinx.coroutines.flow.StateFlow
import com.createfuture.takehome.api.common.Result

interface CharacterRepository {
    val characters: StateFlow<Result<List<GotCharacter>>>

    suspend fun fetchCharacters(
        refresh: Boolean,
    ): Result<List<GotCharacter>>
}