package com.architects.pokearch.ui.features.details.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architects.pokearch.R
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.ui.components.dialogs.ArchDialog
import com.architects.pokearch.ui.components.extensions.abilityColor
import com.architects.pokearch.ui.components.placeHolders.ErrorScreen
import com.architects.pokearch.ui.components.progressIndicators.ArchLoadingIndicator
import com.architects.pokearch.ui.features.details.extensions.getGradientImageAndColors
import com.architects.pokearch.ui.features.details.state.DetailUiState
import com.architects.pokearch.ui.features.details.state.DetailUiState.Success
import com.architects.pokearch.ui.features.details.viewModel.DetailViewModel
import com.architects.pokearch.ui.theme.SetStatusBarColor

@Composable
fun DetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val dialogState by viewModel.dialogState.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.getPokemonDetails()
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {

        when (val state = uiState) {
            is DetailUiState.Loading -> {
                ArchLoadingIndicator()
            }

            is DetailUiState.Error -> {
                ErrorScreen()
            }

            is Success -> {
                DetailSuccessScreen(
                    state = state
                )
            }
        }

        DetailTopBar(
            onBack = onBack,
            uiState = uiState,
            onFavorite = { viewModel.toggleFavorite() }
        )
    }

    dialogState?.let {
        ArchDialog(data = it)
    }

}

@Composable
private fun DetailSuccessScreen(
    state: Success,
    modifier: Modifier = Modifier,
) {

    val (image, colors) = getGradientImageAndColors(pokemonInfo = state.pokemonInfo)

    colors.firstOrNull()?.let { SetStatusBarColor(it) }

    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
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
            text = state.pokemonInfo.name.capitalize(Locale.current)
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
private fun Title(
    text: String,
    modifier: Modifier = Modifier,
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
    pokemonInfo: PokemonInfo,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        pokemonInfo.types.forEach {
            val typeName = it.type.name.capitalize(Locale.current)
            TypeItem(
                name = typeName,
                containerColor = typeName.abilityColor()
            )
        }
    }
}

@Composable
private fun TypeItem(
    name: String,
    containerColor: Color,
    modifier: Modifier = Modifier,
) {
    Text(
        text = name,
        color = containerColor,
        textAlign = TextAlign.Center,
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
            .sizeIn(minWidth = 84.dp)
    )
}

@Composable
fun BaseStatsTitle(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.headlineSmall,
) {
    Text(
        modifier = modifier,
        text = text,
        style = style
    )
}
