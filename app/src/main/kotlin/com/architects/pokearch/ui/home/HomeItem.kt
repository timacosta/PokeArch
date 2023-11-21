package com.architects.pokearch.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import coil.compose.rememberAsyncImagePainter
import com.architects.pokearch.R
import com.architects.pokearch.core.model.Pokemon
import com.architects.pokearch.ui.components.extensions.GetColorsBackground
import com.architects.pokearch.ui.components.extensions.buildImageRequest
import com.architects.pokearch.ui.components.image.PokeArchAsyncImage

@Composable
fun HomeItem(
    pokemon: Pokemon,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    val image = rememberAsyncImagePainter(
        model = pokemon.getImageUrl().buildImageRequest(LocalContext.current)
    )

    val colorDefault = MaterialTheme.colorScheme.surfaceVariant
    val colorsDefault = listOf(colorDefault, colorDefault)

    var colors by remember { mutableStateOf(colorsDefault) }

    image.GetColorsBackground {
        colors = it.ifEmpty { colorsDefault }
    }

    Card(
        modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.card_external_padding))
            .clickable { onItemClick(pokemon.getIndex()) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Brush.linearGradient(colors))
                .fillMaxSize()
                .padding(dimensionResource(id = R.dimen.card_internal_padding))
        ) {
            PokeArchAsyncImage(asyncImagePainter = image, contentDescription = pokemon.name)
            Text(text = pokemon.name.capitalize(Locale.current))
        }
    }
}

