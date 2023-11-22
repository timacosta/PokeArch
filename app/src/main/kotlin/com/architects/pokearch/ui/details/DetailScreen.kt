package com.architects.pokearch.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.architects.pokearch.ui.components.extensions.GetColorsBackground
import com.architects.pokearch.ui.components.extensions.buildImageRequest
import com.architects.pokearch.ui.components.image.PokeArchAsyncImage
import com.architects.pokearch.ui.components.progressIndicators.PokeArchLoadingIndicator
import com.architects.pokearch.ui.details.state.DetailUiState

@Composable
fun DetailScreen(
    pokemonId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
) {

    val uiState by viewModel.pokemonDetailInfo.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = pokemonId) {
        viewModel.getCurrentPokemonId(pokemonId)
    }

    Container {
        when (val state = uiState) {
            is DetailUiState.Loading -> {
                PokeArchLoadingIndicator()
            }

            is DetailUiState.Error -> {
                Text(text = "Error")
            }

            is DetailUiState.Success -> {
                DetailSuccessScreen(state)
            }
        }
    }
}

@Composable
private fun DetailSuccessScreen(state: DetailUiState.Success) {

    val image = rememberAsyncImagePainter(
        model = state.pokemonInfo.getImageUrl().buildImageRequest(LocalContext.current)
    )

    val colorDefault = MaterialTheme.colorScheme.surfaceVariant
    val colorsDefault = listOf(colorDefault, colorDefault)

    var colors by remember { mutableStateOf(colorsDefault) }

    image.GetColorsBackground {
        colors = it.ifEmpty { colorsDefault }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 24.dp,
                        bottomEnd = 24.dp
                    )
                )
                .background(Brush.linearGradient(colors)),
            contentAlignment = Alignment.Center
        ) {
            PokeArchAsyncImage(
                modifier = Modifier.size(240.dp),
                asyncImagePainter = image,
                contentDescription = null
            )
        }

        Title(text = state.pokemonInfo.capitalizedName())

        state.pokemonInfo.types.forEach {
            Text(text = "${it.type}")
        }
    }
}

@Composable
private fun Container(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        propagateMinConstraints = true,
    ) {
        content()
    }
}

@Composable
private fun Title(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineLarge
) {
    Text(
        text = text,
        modifier = modifier,
        style = style
    )
}
