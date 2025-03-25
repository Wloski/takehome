package com.createfuture.takehome.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.createfuture.takehome.R
import com.createfuture.takehome.compose.character.CharacterCard
import com.createfuture.takehome.model.GotCharacter
import com.createfuture.takehome.ui.theme.AppTheme

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
) {
    val viewState: HomeViewState by viewModel.uiState.collectAsState()
    HomeContent(
        viewState = viewState,
        searchState = viewModel.searchTextState,
        emitViewAction = viewModel::handleViewAction
    )
}

@Composable
fun HomeContent(
    viewState: HomeViewState,
    searchState: String,
    emitViewAction: (HomeViewActions) -> Unit,
) {
    when {
        viewState.isLoading -> HomeLoading()
        viewState.error -> Text("Something went wrong...")
        viewState.characters.isNotEmpty() -> CharacterDetailsView(
            viewState = viewState,
            searchState = searchState,
            emitViewAction = emitViewAction
        )
    }
}

@Composable
fun CharacterDetailsView(
    viewState: HomeViewState,
    searchState: String,
    emitViewAction: (HomeViewActions) -> Unit,
) {
    var search by rememberSaveable { mutableStateOf(searchState) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paint(painterResource(id = R.drawable.img_characters), contentScale = ContentScale.FillBounds)
    ) {

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .alpha(0.5f),
            value = search,
            maxLines = 1,
            shape = ShapeDefaults.Small,
            onValueChange = {
                search = it
                emitViewAction(HomeViewActions.OnSearchChange(it))
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                )
            },
            label = { Text("Search") }

        )

        LazyColumn(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val characters = viewState.filteredCharacters.ifEmpty { viewState.characters }
            items(characters) { character ->
                CharacterCard(character)
            }
        }
    }
}

@Composable
fun HomeLoading() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .align(Alignment.Center),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
@Preview
fun HomeContentPreview() {
    AppTheme {
        HomeContent(
            viewState = HomeViewState(
                characters = listOf(
                    GotCharacter(
                        name = "Character One",
                        gender = "Male",
                        culture = "English",
                        born = "UK",
                        died = "N/A",
                        aliases = listOf("Test1", "Test2"),
                        tvSeries = "I, II, IV, VI",
                        playedBy = listOf("Person1", "Person2")
                    ),
                    GotCharacter(
                        name = "Character One",
                        gender = "Male",
                        culture = "English",
                        born = "UK",
                        died = "N/A",
                        aliases = listOf("Test1", "Test2"),
                        tvSeries = "I, II",
                        playedBy = listOf("Person1", "Person2")
                    )
                )
            ),
            searchState = "",
            emitViewAction = {}
        )
    }
}

@Composable
@Preview
fun HomeContentLoadingPreview() {
    AppTheme {
        HomeContent(
            viewState = HomeViewState(
                isLoading = true,
                characters = emptyList()
            ),
            searchState = "",
            emitViewAction = {}
        )
    }
}

@Composable
@Preview
fun HomeContentErrorPreview() {
    AppTheme {
        HomeContent(
            viewState = HomeViewState(
                error = true,
                characters = emptyList()
            ),
            searchState = "",
            emitViewAction = {}
        )
    }
}