package com.architects.pokearch.ui.features.shakeNCatch.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architects.pokearch.R
import com.architects.pokearch.ui.components.animations.AnimatedPokeball
import com.architects.pokearch.ui.components.animations.LoadingPokeball
import com.architects.pokearch.ui.features.shakeNCatch.viewModel.ShakeNCatchViewModel
import com.architects.pokearch.ui.theme.SetStatusBarColor

@Composable
fun ShakeNCatchScreen(
    modifier: Modifier = Modifier,
    viewModel: ShakeNCatchViewModel = hiltViewModel(),
    onNavigationClick: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true){
        viewModel.backFromDetail()
    }

    SetStatusBarColor()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        when {
            !uiState.openedPokeball -> {
                ShakeNCatchBody(acceleration = uiState.acceleration)
            }

            uiState.isLoading -> {
                LoadingPokeball()
            }

            uiState.pokemonInfo != null -> {
                LaunchedEffect(key1 = uiState.pokemonInfo) {
                    onNavigationClick(uiState.pokemonInfo?.id ?: 0)
                    viewModel.afterNavigation()
                }
            }

            else -> {
                Text(text = "Error")
            }
        }
    }
}

const val AccelerationMultiplier = 10

@Composable
private fun ShakeNCatchBody(
    acceleration: Float,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        AnimatedPokeball(acceleration * AccelerationMultiplier)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = stringResource(R.string.shake_pokeball))
    }
}
