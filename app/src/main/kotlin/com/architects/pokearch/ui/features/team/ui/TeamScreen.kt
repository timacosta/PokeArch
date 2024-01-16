package com.architects.pokearch.ui.features.team.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architects.pokearch.ui.components.progressIndicators.ArchLoadingIndicator

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun TeamScreen(
    onNavigationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TeamViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Container(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {

        when (val state = uiState) {
            TeamUiState.Loading -> {
                ArchLoadingIndicator()
            }

            is TeamUiState.Success -> {
                TeamSuccessView(
                    state = state,
                    onItemClick = { pokemonId ->
                        onNavigationClick(pokemonId)
                    },
                )
            }

            is TeamUiState.Error -> {
                Text(
                    text = "Something went wrong"
                )
            }
        }
    }
}

@Composable
private fun Container(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier,
        propagateMinConstraints = true,
    ) {
        content()
    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun TeamSuccessView(
    state: TeamUiState.Success,
    onItemClick: (Int) -> Unit,
) {
    val pokemons = state.pokemonTeam

    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(4),
    ) {
        items(count = pokemons.size) { index ->
            val pokemon = pokemons[index]
            TeamItem(
                pokemon = pokemon,
                onItemClick = { pokemonId ->
                    onItemClick(pokemonId)
                }
            )
        }
    }
}
