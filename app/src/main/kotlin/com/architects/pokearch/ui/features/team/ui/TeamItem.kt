package com.architects.pokearch.ui.features.team.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
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
        model = pokemon.getSmallSpriteArtworkImageUrl().buildImageRequest(LocalContext.current)
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
        modifier = modifier
            .clickable { onItemClick(pokemon.id) }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .modifyIf(image.state is AsyncImagePainter.State.Loading) {
                    it.shimmerEffect()
                }
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
}

@Preview
@Composable
private fun TeamItemPreview() {

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        LazyVerticalGrid(
            state = rememberLazyGridState(),
            columns = GridCells.Fixed(4),
        ) {
            items(count = 10) { index ->
                TeamItem(
                    pokemon = PokemonInfo(
                        id = 1,
                        name = "Pikachu",
                        height = 4,
                        weight = 60,
                        experience = 112,
                        types = emptyList(),
                        stats = emptyList(),
                        team = true
                    ),
                    onItemClick = {}
                )
            }
        }
    }
}
