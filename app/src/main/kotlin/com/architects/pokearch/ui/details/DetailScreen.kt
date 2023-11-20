package com.architects.pokearch.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.architects.pokearch.ui.components.image.PokeArchAsyncImage
import com.architects.pokearch.ui.components.progressIndicators.PokeArchLoadingIndicator
import com.architects.pokearch.ui.details.state.DetailUiState
import com.architects.pokearch.ui.home.buildImageRequest

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

@Composable
private fun DetailSuccessScreen(state: DetailUiState.Success) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
        propagateMinConstraints = true,
    ) {

        val image = rememberAsyncImagePainter(
            model = state.pokemonInfo.getImageUrl().buildImageRequest(LocalContext.current)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
        ) {
            Spacer(modifier = Modifier.size(62.dp))
            PokeArchAsyncImage(
                modifier = Modifier.size(260.dp),
                asyncImagePainter = image,
                contentDescription = null
            )
            Text(
                text = state.pokemonInfo.capitalizedName(),
                style = MaterialTheme.typography.headlineLarge
            )
            Text(text = "Experience ${state.pokemonInfo.experience}")
            state.pokemonInfo.types.forEach {
                Text(text = "${it.type}")
            }
        }
    }
}
