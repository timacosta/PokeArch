package com.architects.pokearch.ui.features.shakeNCatch.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architects.pokearch.ui.components.progressIndicators.ArchLoadingIndicator
import com.architects.pokearch.ui.features.shakeNCatch.viewModel.ShakeNCatchViewModel

@Composable
fun ShakeNCatchScreen(
    modifier: Modifier = Modifier,
    viewModel: ShakeNCatchViewModel = hiltViewModel(),
    onNavigationClick: (Int) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        when {
            !uiState.openedPokeball -> { Text(text = "Shake phone") }
            uiState.isLoading -> { ArchLoadingIndicator() }
            uiState.pokemonInfo != null -> {
                onNavigationClick(uiState.pokemonInfo?.id ?: 0)
            }
            else -> { Text(text = "Error") }
        }
    }

}
