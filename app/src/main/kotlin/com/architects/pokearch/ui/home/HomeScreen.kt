package com.architects.pokearch.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architects.pokearch.R
import com.architects.pokearch.ui.components.progressIndicators.ArchLoadingIndicator
import com.architects.pokearch.ui.home.state.HomeUiState
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    pokemonName: String,
    onNavigationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = pokemonName) {
        viewModel.getPokemonList(pokemonName)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
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
                    },
                    onLoadMore = {
                        scope.launch {
                            viewModel.onLoadMore(pokemonName)
                        }
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
    onLoadMore: () -> Unit,
) {
    val lazyGridState = rememberLazyGridState()

    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = lazyGridState.layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false

            lastVisibleItem.index == lazyGridState.layoutInfo.totalItemsCount - 1
        }
    }

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.grid_cell_min_size))
    ) {
        items(state.pokemonList) {
            HomeItem(
                pokemon = it,
                onItemClick = { pokemonId ->
                    onItemClick(pokemonId)
                }
            )
        }
    }

    lazyGridState.OnBottomReached {
        onLoadMore()
    }
}

@Composable
fun LazyGridState.OnBottomReached(
    onLoadMore: () -> Unit,
) {
    val shouldLoadMore = remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf false

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1
        }
    }

    LaunchedEffect(shouldLoadMore) {
        snapshotFlow { shouldLoadMore.value }
            .distinctUntilChanged()
            .filter { it }
            .collect { onLoadMore() }
    }
}