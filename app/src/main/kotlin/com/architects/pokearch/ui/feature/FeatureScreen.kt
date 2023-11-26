package com.architects.pokearch.ui.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.hilt.navigation.compose.hiltViewModel
import com.architects.pokearch.ui.components.progressIndicators.ArchLoadingIndicator

@Composable
fun FeatureScreen(
    modifier: Modifier = Modifier,
    viewModel: FeatureViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        when {
            !uiState.openedPokeball -> { Text(text = "Shake phone") }
            uiState.isLoading -> { ArchLoadingIndicator() }
            uiState.pokemonInfo != null -> {
                Text("${uiState.pokemonInfo?.name?.capitalize(Locale.current)}")
            }
            else -> { Text(text = "Error") }
        }
    }

}
