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
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.architects.pokearch.R
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.ui.components.animations.shimmerEffect
import com.architects.pokearch.ui.components.extensions.GetColorsBackground
import com.architects.pokearch.ui.components.extensions.buildImageRequest
import com.architects.pokearch.ui.components.extensions.modifyIf
import com.architects.pokearch.ui.components.image.ArchAsyncImage

@Composable
fun HomeItem(
    modifier: Modifier = Modifier,
    pokemon: Pokemon,
    onItemClick: (Int) -> Unit,
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
                .modifyIf(image.state is AsyncImagePainter.State.Loading) {
                    it.shimmerEffect()
                }
                .padding(dimensionResource(id = R.dimen.card_internal_padding))
        ) {
            ArchAsyncImage(asyncImagePainter = image, contentDescription = pokemon.name)
            Text(text = pokemon.name.capitalize(Locale.current))
        }
    }
}

