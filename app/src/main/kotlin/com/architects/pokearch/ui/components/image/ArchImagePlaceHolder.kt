package com.architects.pokearch.ui.components.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ArchImagePlaceHolder(
    modifier: Modifier = Modifier,
    size: Dp = 150.dp
) {
    Box(
        modifier = modifier
            .size(size)
    )
}