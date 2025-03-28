package com.createfuture.takehome.api.home.repository

import com.createfuture.takehome.api.home.model.NetworkCharacter
import com.createfuture.takehome.api.home.service.CharacterService
import io.mockk.coEvery
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import com.createfuture.takehome.api.common.Result
import com.createfuture.takehome.api.home.model.toCharacter
import io.mockk.coVerify
import io.mockk.mockk
import okhttp3.ResponseBody.Companion.toResponseBody

@ExperimentalCoroutinesApi
class CharacterRepositoryTest {

    private lateinit var repository: CharacterRepositoryImpl
    private val service: CharacterService = mockk()

    @Before
    fun setup() {
        repository = CharacterRepositoryImpl(service)
    }

    @Test
    fun `fetchCharacters emits Success when API returns valid character data`() = runTest {
        val response = Response.success(characterList)
        coEvery { service.getCharacters() } returns response

        val result = repository.fetchCharacters(refresh = true)

        assertTrue(result is Result.Success)
        assertEquals(characterList.map { it.toCharacter() }, (result as Result.Success).data)
    }

    @Test
    fun `fetchCharacters emits Failure when API response is unsuccessful`() = runTest {
        val response = Response.error<List<NetworkCharacter>>(400, "".toResponseBody())
        coEvery { service.getCharacters() } returns response

        val result = repository.fetchCharacters(refresh = true)

        assertTrue(result is Result.Failure)
    }

    @Test
    fun `fetchCharacters returns cached result when refresh is false and data exists`() = runTest {
        val response = Response.success(characterList)
        coEvery { service.getCharacters() } returns response

        repository.fetchCharacters(refresh = true)
        val result = repository.fetchCharacters(refresh = false)

        assertTrue(result is Result.Success)
        assertEquals(characterList.map { it.toCharacter() }, (result as Result.Success).data)

        coVerify(exactly = 1) { service.getCharacters() }
    }
}