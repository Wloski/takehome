package com.createfuture.takehome.ui.home

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.createfuture.takehome.ui.theme.AppTheme
import org.junit.Rule
import org.junit.Test

class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun homeContent_shows_loading_when_isLoading() {
        composeTestRule.setContent {
            AppTheme {
                HomeContent(
                    viewState = HomeViewState(isLoading = true),
                    searchState = "",
                    emitViewAction = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("HomeScreenLoadingIndicator").assertExists()
    }

    @Test
    fun homeContent_shows_error_when_error() {
        composeTestRule.setContent {
            AppTheme {
                HomeContent(
                    viewState = HomeViewState(error = true),
                    searchState = "",
                    emitViewAction = {}
                )
            }
        }

        composeTestRule.onNodeWithTag("ErrorText").assertExists()
    }

    // TODO
    //fun homeContent_showsCharacters_whenCharactersAvailable
    //fun homeContent_updates_search_field_on_typing()

}