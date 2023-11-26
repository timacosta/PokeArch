package com.architects.pokearch.ui.feature

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FeatureScreen(
    modifier: Modifier = Modifier,
    viewModel: FeatureViewModel = hiltViewModel(),
) {
    val openPokeball by viewModel.openPokeball.collectAsState()

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {

        Text(text = "Pokeball open $openPokeball")
    }

}
