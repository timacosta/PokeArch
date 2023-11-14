package com.architects.pokearch.ui.home

import android.content.Context
import android.graphics.drawable.Drawable
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.architects.pokearch.R
import com.architects.pokearch.core.model.Pokemon
import com.architects.pokearch.ui.components.image.ArchAsyncImage

@Composable
fun HomeItem(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
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
                .padding(dimensionResource(id = R.dimen.card_internal_padding))
        ) {
            ArchAsyncImage(asyncImagePainter = image, contentDescription = pokemon.name)
            Text(text = pokemon.name.capitalize(Locale.current))
        }
    }
}

private fun String.buildImageRequest(context: Context) =
    if (this.contains("http")) {
        ImageRequest.Builder(context)
            .data(this)
            .size(Size.ORIGINAL)
            .allowHardware(false)
            .build()
    } else null

@Composable
private fun AsyncImagePainter.GetColorsBackground(onGetColors: (List<Color>) -> Unit) {
    getDrawableSuccessState()?.let { drawable ->
        onGetColors(drawable.generatePalette().extractColors())
    }
}

@Composable
private fun AsyncImagePainter.getDrawableSuccessState(): Drawable? =
    if (this.state is AsyncImagePainter.State.Success)
        (this.state as AsyncImagePainter.State.Success).result.drawable
    else null

private fun Drawable.generatePalette() = Palette.from(this.toBitmap()).generate()

private fun Palette.extractColors() =
    listOfNotNull(
        this.lightVibrantSwatch?.let { Color(it.rgb) },
        this.dominantSwatch?.let { Color(it.rgb) }
    ).let { colors ->
        if (colors.size == 1) colors + colors else colors
    }
