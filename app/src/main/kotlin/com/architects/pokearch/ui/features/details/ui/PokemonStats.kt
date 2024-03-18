package com.architects.pokearch.ui.features.details.ui

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.Stat
import com.architects.pokearch.domain.model.Stats
import com.architects.pokearch.ui.components.extensions.statColor

private const val PROGRESS_TRACK_COLOR_ALPHA = 0.1f
private const val STAT_ITEM_WEIGHT = 0.5f
private const val STAT_NAME_ALPHA = 0.8f
private const val STAT_NAME_WEIGHT = 0.1f

@Composable
internal fun PokemonStats(
    pokemonInfo: PokemonInfo,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        pokemonInfo.stats.forEach {
            StatItem(
                stats = it
            )
            Spacer(modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
private fun StatItem(
    stats: Stats,
    modifier: Modifier = Modifier,
    height: Dp = 22.dp,
) {

    val animationProgress = remember {
        Animatable(
            initialValue = 0f,
        )
    }

    LaunchedEffect(Unit) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 14 * stats.value,
                easing = LinearEasing
            )
        )
    }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {

        StatName(stat = stats.stat)

        val progress = stats.value.toFloat() / stats.maxValue
        val animatedProgress = progress * animationProgress.value

        //val progressColor = if (progress >= .5f) Color.Blue else Color.Red
        val progressTrackColor = MaterialTheme.colorScheme.outline.copy(PROGRESS_TRACK_COLOR_ALPHA)

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .weight(STAT_ITEM_WEIGHT)
                .height(height)
                .drawBehind {
                    drawRoundRect(
                        color = progressTrackColor,
                        topLeft = Offset.Zero,
                        size = size,
                        cornerRadius = CornerRadius(size.height, size.height),
                    )

                    drawRoundRect(
                        color = stats.stat.name.statColor(),
                        topLeft = Offset.Zero,
                        size = Size(width = size.width * animatedProgress, height = size.height),
                        cornerRadius = CornerRadius(size.height, size.height),
                    )
                }
        ) {
            Text(
                text = "${stats.value}/${stats.maxValue}",
            )
        }
    }
}

@Composable
private fun RowScope.StatName(stat: Stat) {
    Text(
        text = stat.name,
        color = MaterialTheme.colorScheme.onBackground.copy(STAT_NAME_ALPHA),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.weight(STAT_NAME_WEIGHT)
    )
}
