package com.architects.pokearch.ui.features.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import com.architects.pokearch.R
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.ui.components.placeHolders.NoSearchResultPlaceHolder
import com.architects.pokearch.ui.features.home.state.HomeUiState

@Composable
fun HomeSuccessScreen(
    state: HomeUiState.Success,
    onItemClick: (Int) -> Unit,
) {
    val pokemons: LazyPagingItems<Pokemon> = state.pokemonList.collectAsLazyPagingItems()

    if (isSearchResultEmpty(
            notLoading = pokemons.loadState.source.refresh is LoadState.NotLoading,
            endOfPaginationReached = pokemons.loadState.append.endOfPaginationReached,
            noItems = pokemons.itemCount < 1
        )
    )
        NoPokemonSearchResult()
    else
        PokemonVerticalGrid(pokemons, onItemClick)

}

@Composable
private fun NoPokemonSearchResult() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        NoSearchResultPlaceHolder(
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}


@Composable
private fun PokemonVerticalGrid(
    pokemons: LazyPagingItems<Pokemon>,
    onItemClick: (Int) -> Unit,
) {
    val lazyGridState = rememberLazyGridState()

    LazyVerticalGrid(
        state = lazyGridState,
        columns = GridCells.Adaptive(dimensionResource(id = R.dimen.grid_cell_min_size))
    ) {
        items(count = pokemons.itemCount,
            key = pokemons.itemKey { it.getIndex() },
            contentType = pokemons.itemContentType { "contentType" }
        ) { index ->
            val pokemon = pokemons[index]
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

private fun isSearchResultEmpty(
    notLoading: Boolean,
    endOfPaginationReached: Boolean,
    noItems: Boolean,
) = notLoading && endOfPaginationReached && noItems

