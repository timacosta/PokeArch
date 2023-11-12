package com.architects.pokearch.ui.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architects.pokearch.ui.components.progressIndicators.PokeArchLoadingIndicator
import com.architects.pokearch.ui.details.state.DetailUiState

@Composable
fun DetailScreen(
    pokemonId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
) {

    val uiState by viewModel.pokemonDetailInfo.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = pokemonId) {
        viewModel.getCurrentPokemonId(pokemonId)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {

        when (val state = uiState) {
            is DetailUiState.Loading -> {
                PokeArchLoadingIndicator()
            }

            is DetailUiState.Error -> {
                Text(text = "Error")
            }

            is DetailUiState.Success -> {
                Column {
                    Text(text = "Name ${state.pokemonInfo.name}")
                    Text(text = "Experience ${state.pokemonInfo.experience}")
                    state.pokemonInfo.types.forEach {
                        Text(text = "${it.type}")
                    }
                }
            }
        }
    }
}
