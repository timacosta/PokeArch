package com.architects.pokearch.ui.features.team.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.ui.components.animations.shimmerEffect
import com.architects.pokearch.ui.components.extensions.buildImageRequest
import com.architects.pokearch.ui.components.extensions.modifyIf
import com.architects.pokearch.ui.components.image.ArchAsyncImage


@Composable
fun TeamItem(
    pokemon: PokemonInfo,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
) {

    val image = rememberAsyncImagePainter(
        model = pokemon.getSpriteUrl().buildImageRequest(LocalContext.current)
    )
    Box(
        modifier = modifier
            .padding(start = 4.dp, end = 4.dp)
            .background(Color.Transparent)
            .fillMaxSize()
            .clip(shape = RoundedCornerShape(8.dp))
            .modifyIf(image.state is AsyncImagePainter.State.Loading) {
                it.shimmerEffect()
            }
            .clickable { onItemClick(pokemon.id) }
    ) {
        ArchAsyncImage(
            modifier = Modifier.height(100.dp),
            asyncImagePainter = image,
            placeHolderSize = 75.dp,
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }
}
