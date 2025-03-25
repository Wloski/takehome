package com.createfuture.takehome.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.Snapshot.Companion.withMutableSnapshot
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.createfuture.takehome.api.common.Result
import com.createfuture.takehome.api.home.repository.CharacterRepository
import com.createfuture.takehome.model.GotCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CharacterRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeViewState())
    val uiState: StateFlow<HomeViewState> = _uiState

    @OptIn(SavedStateHandleSaveableApi::class)
    var searchTextState: String by savedStateHandle.saveable {
        mutableStateOf("")
    }

    init {
        fetchCharacters()

        viewModelScope.launch {
            repository.characters.collect {
                when (it) {
                    is Result.Success -> {
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                characters = it.data
                            )
                        }
                    }

                    is Result.Failure -> {
                        _uiState.update { state ->
                            state.copy(error = true)
                        }
                    }

                    is Result.Loading -> {
                        _uiState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }

                    is Result.None -> {
                        _uiState.update { state ->
                            state.copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    fun handleViewAction(actions: HomeViewActions) {
        when (actions) {
            is HomeViewActions.OnSearchChange -> {
                searchTextState = actions.search
                withMutableSnapshot {
                    searchTextState = actions.search
                }

                _uiState.update { state ->
                    state.copy(
                        filteredCharacters = state.characters.filter { it.name.contains(actions.search, ignoreCase = true) }
                    )
                }
            }
        }
    }

    // TODO If i had more time i would setup access to data via a use case and not fetch the data directly from the repository
    private fun fetchCharacters() {
        viewModelScope.launch {
            repository.fetchCharacters(true)
        }
    }
}

data class HomeViewState(
    val isLoading: Boolean = false,
    val characters: List<GotCharacter> = emptyList(),
    val filteredCharacters: List<GotCharacter> = emptyList(),
    val error: Boolean = false,
)

sealed interface HomeViewActions {
    class OnSearchChange(val search: String) : HomeViewActions
}