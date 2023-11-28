package com.architects.pokearch.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architects.pokearch.R
import com.architects.pokearch.ui.components.progressIndicators.ArchLoadingIndicator
import com.architects.pokearch.ui.home.state.HomeUiState

@Composable
fun HomeScreen(
    onNavigationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    pokemonName: String,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = pokemonName) {
        viewModel.getPokemonList(pokemonName)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {

        when (val state = uiState) {
            is HomeUiState.Loading -> {
                ArchLoadingIndicator()
            }

            is HomeUiState.Success -> {
                HomeSuccessScreen(
                    state = state,
                    onItemClick = { pokemonId ->
                        onNavigationClick(pokemonId)
                    }
                )
            }

            is HomeUiState.Error -> {
                Text(text = "Something went wrong")
            }
        }
    }
}

@Composable
private fun HomeSuccessScreen(
    state: HomeUiState.Success,
    onItemClick: (Int) -> Unit,
) {
    LazyVerticalGrid(columns = GridCells.Adaptive(dimensionResource(id = R.dimen.grid_cell_min_size))) {
        items(state.pokemonList) {
            HomeItem(
                pokemon = it,
                onItemClick = { pokemonId ->
                    onItemClick(pokemonId)
                }
            )
        }
    }
}
