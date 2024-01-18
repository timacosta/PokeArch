package com.architects.pokearch.ui.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architects.pokearch.ui.components.animations.LoadingPokeball
import com.architects.pokearch.ui.components.dialogs.ArchDialog
import com.architects.pokearch.ui.components.placeHolders.NoSearchResultPlaceHolder
import com.architects.pokearch.ui.features.home.state.HomeUiState
import com.architects.pokearch.ui.features.home.viewModel.HomeViewModel
import com.architects.pokearch.ui.theme.SetStatusBarColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    pokemonName: String,
    onNavigationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()

    SetStatusBarColor()

    LaunchedEffect(key1 = pokemonName) {
        viewModel.getPokemonList(pokemonName)
    }

    dialogState?.let {
        ArchDialog(data = it)
    }

    Container(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 4.dp)
    ) {

        when (val state = uiState) {
            //TODO: Not shown because state is set directly to TRUE when
            is HomeUiState.Loading -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier.fillMaxSize()
                ) {
                    LoadingPokeball(
                        modifier = Modifier.size(100.dp)
                    )
                }
            }

            is HomeUiState.Success -> {
                HomeSuccessScreen(
                    state = state,
                    onItemClick = { pokemonId ->
                        onNavigationClick(pokemonId)
                    },
                )
            }

            is HomeUiState.NoSearchResult -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier.fillMaxSize()
                ) {
                    NoSearchResultPlaceHolder(
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }

            is HomeUiState.Error -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = modifier.fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "Something went wrong"
                    )
                }
            }
        }
    }
}

@Composable
private fun Container(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier,
        propagateMinConstraints = true,
    ) {
        content()
    }
}
