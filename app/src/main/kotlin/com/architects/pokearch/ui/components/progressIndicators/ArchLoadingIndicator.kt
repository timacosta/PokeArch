package com.architects.pokearch.ui.components.progressIndicators

import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ArchLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    trackColor: Color = MaterialTheme.colorScheme.secondary,
) {
    CircularProgressIndicator(
        modifier = modifier.width(64.dp),
        color = color,
        trackColor = trackColor,
    )
}
