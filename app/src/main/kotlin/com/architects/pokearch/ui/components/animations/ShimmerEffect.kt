package com.architects.pokearch.ui.components.animations

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize
import com.architects.pokearch.ui.theme.ShimmerColorMain
import com.architects.pokearch.ui.theme.ShimmerColorSecondary

fun Modifier.shimmerEffect(): Modifier = composed {

    var size by remember { mutableStateOf(IntSize.Zero) }
    val transition = rememberInfiniteTransition(label = "shimmerInfiniteTransition")

    val startOffsetX by transition.animateFloat(
        initialValue = -2.50F * size.width.toFloat(),
        targetValue = 2.50F * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000)
        ),
        label = "startTransition"
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                ShimmerColorMain,
                ShimmerColorSecondary,
                ShimmerColorMain,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), 2 * size.height.toFloat()),
        )
    ).onGloballyPositioned { coordinates ->
        size = coordinates.size
    }
}
