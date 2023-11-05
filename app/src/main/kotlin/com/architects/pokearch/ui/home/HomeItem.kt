package com.architects.pokearch.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
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
            .allowHardware(false)
            .build()
    )

    val colorDefault = MaterialTheme.colorScheme.background
    val colorsDefault = listOf(colorDefault, colorDefault)

    var colors by remember { mutableStateOf(colorsDefault) }

    image.GetColorsBackground {
        colors = it.ifEmpty { colorsDefault }
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
                .background(Brush.linearGradient(colors))
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.card_internal_padding))
        ) {
            Image(painter = image, contentDescription = pokemon.name)
            Text(text = pokemon.name.capitalize(Locale.current))
        }
    }
}

@Composable
private fun AsyncImagePainter.GetColorsBackground(onGetColor: (List<Color>) -> Unit) {
    if (this.state is AsyncImagePainter.State.Success) {
        var colors = emptyList<Color>()
        val palette =
            Palette.from((this.state as AsyncImagePainter.State.Success).result.drawable.toBitmap())
                .generate()
        palette.lightVibrantSwatch?.let { colors = colors.plus(Color(it.rgb)) }
        palette.dominantSwatch?.let { colors = colors.plus(Color(it.rgb)) }

        if (colors.size == 1) {
            colors = colors.plus(colors[0])
        }

        onGetColor(colors)
    }
}

@Preview
@Composable
fun HomeItemList() {
    val pokemons = PokemonRepository.getPokemons()

    MyPokeArchTheme {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = dimensionResource(id = R.dimen.grid_cell_min_size))
        ) {
            items(pokemons) { pokemon ->
                HomeItem(pokemon = pokemon, onDetailClick = {})
            }
        }
    }
}
