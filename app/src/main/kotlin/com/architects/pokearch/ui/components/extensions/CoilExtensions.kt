package com.architects.pokearch.ui.components.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.compose.AsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size

fun String.buildImageRequest(context: Context) =
    if (this.contains("http")) {
        ImageRequest.Builder(context)
            .data(this)
            .size(Size.ORIGINAL)
            .allowHardware(false)
            .build()
    } else null

@Composable
fun AsyncImagePainter.GetColorsBackground(onGetColors: (List<Color>) -> Unit) {
    getDrawableSuccessState()?.let { drawable ->
        onGetColors(drawable.generatePalette().extractColors())
    }
}

@Composable
fun AsyncImagePainter.getDrawableSuccessState(): Drawable? =
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
