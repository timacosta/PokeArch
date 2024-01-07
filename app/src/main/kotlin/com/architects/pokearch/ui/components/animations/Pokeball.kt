package com.architects.pokearch.ui.components.animations

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.architects.pokearch.R

@Composable
fun AnimatedPokeball(
    acceleration: Float,
    modifier: Modifier = Modifier,
) {
    val rotation = remember { Animatable(acceleration) }

    LaunchedEffect(key1 = acceleration) {
        rotation.animateTo(
            targetValue = acceleration,
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }

    ImagePokeball(
        rotation = rotation,
        modifier = modifier
    )
}

const val AnimationdurationMillis = 300

@Composable
fun LoadingPokeball(
    modifier: Modifier = Modifier,
) {
    val rotation = remember { Animatable(0f) }

    LaunchedEffect(key1 = Unit) {
        rotation.animateTo(
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(AnimationdurationMillis, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            )
        )
    }

    ImagePokeball(
        rotation = rotation,
        modifier = modifier
    )
}

@Composable
private fun ImagePokeball(
    rotation: Animatable<Float, AnimationVector1D>,
    modifier: Modifier = Modifier,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_launcher_pokeball_background),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .height(300.dp)
            .rotate(rotation.value)
    )
}
