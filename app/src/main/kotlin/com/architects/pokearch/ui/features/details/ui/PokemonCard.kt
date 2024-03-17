package com.architects.pokearch.ui.features.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import com.architects.pokearch.ui.components.image.ArchAsyncImage

@Composable
internal fun PokemonCard(
    pokemonImage: AsyncImagePainter,
    gradientColors: List<Color>,
    modifier: Modifier = Modifier,
    imageSize: Dp = 240.dp,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp
                )
            )
            .background(Brush.linearGradient(gradientColors)),
        contentAlignment = Alignment.Center
    ) {
        ArchAsyncImage(
            modifier = Modifier.size(imageSize),
            asyncImagePainter = pokemonImage,
            contentDescription = null
        )
    }
}
