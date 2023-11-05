package com.architects.pokearch.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.architects.pokearch.R
import com.architects.pokearch.core.data.repository.PokemonRepository
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.ui.theme.MyPokeArchTheme

@Composable
fun HomeItem(pokemon: Pokemon, modifier: Modifier = Modifier, onDetailClick: (Int) -> Unit) {

    val image = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
        .data(pokemon.getImageUrl())
        .size(coil.size.Size.ORIGINAL)
        .build())

    if (image.state is AsyncImagePainter.State.Success){
        SideEffect {

        }
    }

    Card(
        modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.card_external_padding))
            .clickable { onDetailClick(pokemon.getId()) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.card_internal_padding))
        ) {
            Image(painter = image, contentDescription = pokemon.name)
            Text(text = pokemon.name.capitalize(Locale.current))
        }
    }
}

@Preview
@Composable
fun HomeItemList() {
    val pokemons = PokemonRepository.getPokemons()

    MyPokeArchTheme {
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = dimensionResource(id = R.dimen.grid_cell_min_size))) {
            items(pokemons) { pokemon ->
                HomeItem(pokemon = pokemon, onDetailClick = {})
            }
        }
    }
}
