package com.createfuture.takehome.api.home.repository

import com.createfuture.takehome.api.common.Result
import com.createfuture.takehome.api.home.model.toCharacter
import com.createfuture.takehome.api.home.service.CharacterApi
import com.createfuture.takehome.api.home.service.CharacterService
import com.createfuture.takehome.model.GotCharacter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val service: CharacterService,
) : CharacterRepository {

    private val _characters: MutableStateFlow<Result<List<GotCharacter>>> = MutableStateFlow(Result.None)
    override val characters: StateFlow<Result<List<GotCharacter>>> = _characters

    override suspend fun fetchCharacters(refresh: Boolean): Result<List<GotCharacter>> {
        if (!refresh && _characters.value is Result.Success) return characters.value

        _characters.value = Result.Loading()

        try {
            service.getCharacters().also {
                if (!it.isSuccessful || it.body().isNullOrEmpty()) _characters.value = Result.Failure(Exception("Failed to get result"))
                else _characters.value = Result.Success(it.body()!!.map { networkCharacter -> networkCharacter.toCharacter() })
            }
        } catch (ex: Exception) {
            throw ex
        }

        return characters.value
    }
}