
package com.architects.pokearch.ui.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.architects.pokearch.R
import com.architects.pokearch.ui.components.MySearchBar
import com.architects.pokearch.ui.home.HomeItem
import com.architects.pokearch.core.data.repository.PokemonRepository

@Composable
fun HomeScreen(modifier: Modifier = Modifier, onNavigationClick: (Int) -> Unit) {

    val pokemonList = PokemonRepository.getPokemons()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column {

            /*MySearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ){

            }*/

            //TODO: Add real implementation
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = dimensionResource(id = R.dimen.grid_cell_min_size))
            ) {
                items(pokemonList) { pokemon ->
                    HomeItem(pokemon = pokemon, onDetailClick = {onNavigationClick(pokemon.getId())})
                }
            }

        }
    }
}