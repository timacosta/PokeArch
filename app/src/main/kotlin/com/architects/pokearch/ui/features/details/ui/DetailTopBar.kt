package com.architects.pokearch.ui.features.details.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.architects.pokearch.ui.features.details.state.DetailUiState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun DetailTopBar(
    onBack: () -> Unit,
    uiState: DetailUiState,
    onFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = { },
        navigationIcon = {
            IconButton(
                onClick = { onBack() },
                colors = iconButtonColors()
            ) {
                Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        },
        actions = {
            if (uiState is DetailUiState.Success) {
                IconButton(
                    onClick = { onFavorite() },
                    colors = iconButtonColors()
                ) {
                    Icon(
                        imageVector =
                        if (uiState.pokemonInfo.team) Icons.Default.Favorite
                        else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                }
            }
        },
        colors = topAppBarColors(),
        modifier = modifier,
    )
}

@Composable
private fun iconButtonColors() =
    IconButtonDefaults.iconButtonColors(
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.30f)
    )

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun topAppBarColors() = TopAppBarDefaults.topAppBarColors(
    containerColor = Color.Transparent,
    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
    actionIconContentColor = MaterialTheme.colorScheme.onSurface
)
