package com.architects.pokearch.ui.features.team.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architects.pokearch.R
import com.architects.pokearch.ui.components.progressIndicators.ArchLoadingIndicator
import com.architects.pokearch.ui.features.team.state.TeamUiState
import com.architects.pokearch.ui.features.team.viewmodel.TeamViewModel
import kotlin.math.max

@Composable
fun TeamScreen(
    onNavigationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TeamViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Container(
        modifier = modifier.fillMaxSize()
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
                    }
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
fun TeamSuccessView(
    state: TeamUiState.Success,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val pokemons = state.pokemonTeam
    val lazyGridState = rememberLazyGridState()

    val isPortrait = LocalConfiguration.current.orientation == Configuration.ORIENTATION_PORTRAIT
    val columns = if (isPortrait) 4 else 8

    val totalRowsWithGarden = max((pokemons.size + columns - 1) / columns, 8)

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Fixed(columns),
        modifier = modifier
    ) {
        items(count = totalRowsWithGarden * columns) { index ->
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.image_cartoon_grass),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )

                if (index < pokemons.size) {
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
