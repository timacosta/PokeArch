package com.architects.pokearch.ui.features.home.ui

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.dimensionResource
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.architects.pokearch.R
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.ui.components.extensions.itemsPaging
import com.architects.pokearch.ui.features.home.state.HomeUiState

@Composable
fun HomeSuccessScreen(
    state: HomeUiState.Success,
    onItemClick: (Int) -> Unit,
) {
    val pokemons: LazyPagingItems<Pokemon> = state.pokemonList.collectAsLazyPagingItems()

    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.grid_cell_min_size))
    ) {
        itemsPaging(items = pokemons) { pokemon ->
            pokemon?.let {
                HomeItem(
                    pokemon = it,
                    onItemClick = { pokemonId ->
                        onItemClick(pokemonId)
                    }
                )
            }
        }
    }
}

