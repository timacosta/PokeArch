package com.architects.pokearch.ui.features.team.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.architects.pokearch.R
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.ui.components.animations.shimmerEffect
import com.architects.pokearch.ui.components.extensions.buildImageRequest
import com.architects.pokearch.ui.components.extensions.modifyIf
import com.architects.pokearch.ui.components.image.ArchAsyncImage

@RequiresApi(Build.VERSION_CODES.P)
@Composable
fun TeamItem(
    pokemon: Pokemon,
    modifier: Modifier = Modifier,
    onItemClick: (Int) -> Unit,
) {

    val image = getImage(pokemon = pokemon)

    Card(
        modifier
            .padding(dimensionResource(id = R.dimen.card_external_padding))
            .clickable { onItemClick(pokemon.getIndex()) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .modifyIf(image.state is AsyncImagePainter.State.Loading) {
                    it.shimmerEffect()
                }
                .padding(dimensionResource(id = R.dimen.card_internal_padding))
        ) {

            Box {
                Text(
                    color = Color.Black,
                    text = "#${pokemon.getIndex()}"
                )
                ArchAsyncImage(
                    modifier = Modifier
                        .fillMaxSize(),
                    asyncImagePainter = image,
                    contentDescription = pokemon.name,
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun getImage(
    pokemon: Pokemon,
): AsyncImagePainter {

    return rememberAsyncImagePainter(
        model = pokemon.getSmallSpriteArtworkImageUrl().buildImageRequest(LocalContext.current)
    )
}
