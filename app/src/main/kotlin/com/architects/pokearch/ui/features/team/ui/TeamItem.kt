package com.architects.pokearch.ui.features.team.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.architects.pokearch.R
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.ui.components.animations.shimmerEffect
import com.architects.pokearch.ui.components.extensions.buildImageRequest
import com.architects.pokearch.ui.components.extensions.modifyIf
import com.architects.pokearch.ui.components.image.ArchAsyncImage

@RequiresApi(Build.VERSION_CODES.P)
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
            containerColor = MaterialTheme.colorScheme.background,
        ),
        modifier = modifier
            .padding(dimensionResource(id = R.dimen.card_external_padding))
            .clickable { onItemClick(pokemon.id) }
    ) {
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .modifyIf(image.state is AsyncImagePainter.State.Loading) {
                    it.shimmerEffect()
                }
                .padding(dimensionResource(id = R.dimen.card_internal_padding)),
        ) {
            Text(
                text = "#${pokemon.id}"
            )
            ArchAsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = dimensionResource(id = R.dimen.image_internal_padding)),
                asyncImagePainter = image,
                contentDescription = pokemon.id.toString(),
                contentScale = ContentScale.Crop
            )
        }

    }
}

@RequiresApi(Build.VERSION_CODES.P)
@Preview
@Composable
private fun TeamItemPreview() {

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {

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
