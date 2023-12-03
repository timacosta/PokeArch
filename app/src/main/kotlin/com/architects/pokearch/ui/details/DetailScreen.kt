package com.architects.pokearch.ui.details

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.architects.pokearch.R
import com.architects.pokearch.core.model.PokemonInfo
import com.architects.pokearch.core.model.Stat
import com.architects.pokearch.core.model.StatsResponse
import com.architects.pokearch.ui.components.extensions.GetColorsBackground
import com.architects.pokearch.ui.components.extensions.abilityColor
import com.architects.pokearch.ui.components.extensions.buildImageRequest
import com.architects.pokearch.ui.components.image.PokeArchAsyncImage
import com.architects.pokearch.ui.components.progressIndicators.PokeArchLoadingIndicator
import com.architects.pokearch.ui.details.state.DetailUiState

@Composable
fun DetailScreen(
    pokemonId: Int,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
) {

    val uiState by viewModel.pokemonDetailInfo.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = pokemonId) {
        viewModel.getCurrentPokemonId(pokemonId)
    }

    Container {
        when (val state = uiState) {
            is DetailUiState.Loading -> {
                PokeArchLoadingIndicator()
            }

            is DetailUiState.Error -> {
                Text(text = "Error")
            }

            is DetailUiState.Success -> {
                DetailSuccessScreen(
                    modifier = modifier,
                    state = state
                )
            }
        }
    }
}

@Composable
private fun DetailSuccessScreen(
    modifier: Modifier = Modifier,
    state: DetailUiState.Success,
) {

    val image = rememberAsyncImagePainter(
        model = state.pokemonInfo.getImageUrl().buildImageRequest(LocalContext.current)
    )

    val colorDefault = MaterialTheme.colorScheme.surfaceVariant
    val colorsDefault = listOf(colorDefault, colorDefault)

    var colors by remember { mutableStateOf(colorsDefault) }

    image.GetColorsBackground {
        colors = it.ifEmpty { colorsDefault }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {

        PokemonCard(
            modifier = Modifier.padding(bottom = 16.dp),
            pokemonImage = image,
            gradientColors = colors
        )

        Title(
            modifier = Modifier.padding(bottom = 12.dp),
            text = state.pokemonInfo.capitalizedName()
        )

        TypeRow(
            modifier = Modifier.padding(bottom = 24.dp),
            pokemonInfo = state.pokemonInfo
        )

        PokemonInfos(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp),
            pokemonInfo = state.pokemonInfo
        )

        BaseStatsTitle(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(id = R.string.base_stats)
        )

        PokemonStats(
            modifier = Modifier.padding(
                vertical = 8.dp,
                horizontal = 20.dp
            ),
            pokemonInfo = state.pokemonInfo
        )
    }
}

@Composable
private fun Container(content: @Composable BoxScope.() -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        propagateMinConstraints = true,
    ) {
        content()
    }
}

@Composable
private fun PokemonCard(
    modifier: Modifier = Modifier,
    pokemonImage: AsyncImagePainter,
    imageSize: Dp = 240.dp,
    gradientColors: List<Color>,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 24.dp,
                    bottomEnd = 24.dp
                )
            )
            .background(Brush.linearGradient(gradientColors)),
        contentAlignment = Alignment.Center
    ) {
        PokeArchAsyncImage(
            modifier = Modifier.size(imageSize),
            asyncImagePainter = pokemonImage,
            contentDescription = null
        )
    }
}

@Composable
private fun Title(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.headlineLarge,
) {
    Text(
        text = text,
        modifier = modifier,
        style = style
    )
}

@Composable
private fun TypeRow(
    modifier: Modifier = Modifier,
    pokemonInfo: PokemonInfo,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        pokemonInfo.types.forEach {
            TypeItem(
                name = it.type.name,
                containerColor = it.type.name.abilityColor()
            )
        }
    }
}

@Composable
private fun TypeItem(
    modifier: Modifier = Modifier,
    name: String,
    containerColor: Color,
) {
    Text(
        text = name.replaceFirstChar { it.uppercaseChar() },
        color = containerColor,
        style = MaterialTheme.typography.bodyLarge,
        modifier = modifier
            .border(
                width = 1.dp,
                color = containerColor,
                shape = CircleShape
            )
            .padding(
                horizontal = 10.dp,
                vertical = 4.dp
            )
    )
}

@Composable
private fun PokemonInfos(
    modifier: Modifier = Modifier,
    pokemonInfo: PokemonInfo,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.outline.copy(.2f))
            .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Row {
                Icon(
                    Icons.Outlined.Scale,
                    contentDescription = null
                )
                Spacer(Modifier.width(4.dp))

                val weightInKg = pokemonInfo.weight / 10f

                Text(
                    text = "$weightInKg kg",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Weight",
                color = MaterialTheme.colorScheme.onBackground.copy(.8f),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Box(
            modifier = Modifier
                .width(1.dp)
                .height(30.dp)
                .background(color = MaterialTheme.colorScheme.outline)
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.weight(1f)
        ) {
            Row {
                Icon(
                    Icons.Outlined.Straighten,
                    contentDescription = null,
                    modifier = Modifier.rotate(90f)
                )
                Spacer(Modifier.width(4.dp))

                val heightInMeter = pokemonInfo.height / 10f

                Text(
                    text = "$heightInMeter m",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(Modifier.height(4.dp))

            Text(
                text = "Height",
                color = MaterialTheme.colorScheme.onBackground.copy(.8f),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun BaseStatsTitle(
    modifier: Modifier = Modifier,
    text: String,
    style: TextStyle = MaterialTheme.typography.headlineSmall,
) {
    Text(
        modifier = modifier,
        text = text,
        style = style
    )
}

@Composable
private fun PokemonStats(
    modifier: Modifier = Modifier,
    pokemonInfo: PokemonInfo,
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
    modifier: Modifier = Modifier,
    height: Dp = 20.dp,
    stats: StatsResponse,
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

        val progressColor = if (progress >= .5f) Color.Blue else Color.Red
        val progressTrackColor = MaterialTheme.colorScheme.outline.copy(.2f)

        Box(
            modifier = Modifier
                .weight(.5f)
                .height(height)
                .drawBehind {
                    drawRoundRect(
                        color = progressTrackColor,
                        topLeft = Offset.Zero,
                        size = size,
                        cornerRadius = CornerRadius(size.height, size.height),
                    )

                    drawRoundRect(
                        color = progressColor,
                        topLeft = Offset.Zero,
                        size = Size(width = size.width * animatedProgress, height = size.height),
                        cornerRadius = CornerRadius(size.height, size.height),
                    )
                }
        )
    }
}

@Composable
private fun RowScope.StatName(stat: Stat) {
    Text(
        text = stat.name,
        color = MaterialTheme.colorScheme.onBackground.copy(.8f),
        style = MaterialTheme.typography.bodyMedium,
        modifier = Modifier.weight(.1f)
    )
}
