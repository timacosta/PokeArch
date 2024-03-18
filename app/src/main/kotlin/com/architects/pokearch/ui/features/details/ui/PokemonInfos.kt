package com.architects.pokearch.ui.features.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.architects.pokearch.domain.model.PokemonInfo

private const val ALPHA_BACKGROUND = .15f
private const val ALPHA_TEXT = .8f

@Composable
internal fun PokemonInfos(
    pokemonInfo: PokemonInfo,
    modifier: Modifier = Modifier,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.outline.copy(ALPHA_BACKGROUND))
            .padding(horizontal = 12.dp)
    ) {
        PokemonInfosItem(
            attributeNumber = pokemonInfo.weight,
            attributeText = "kg",
            attributeName = "Weight",
            iconImageVector = Icons.Outlined.Scale,
            modifier = Modifier.weight(1f),
        )

        Spacer(
            modifier = Modifier
                .width(4.dp)
                .size(88.dp)
                .background(color = MaterialTheme.colorScheme.background)
        )

        PokemonInfosItem(
            attributeNumber = pokemonInfo.height,
            attributeText = "m",
            attributeName = "Height",
            iconImageVector = Icons.Outlined.Straighten,
            iconRotate = 90f,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun PokemonInfosItem(
    attributeNumber: Int,
    attributeText: String,
    attributeName: String,
    iconImageVector: ImageVector,
    modifier: Modifier = Modifier,
    transformAttribute: Float = 10f,
    iconRotate: Float = 0f,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(vertical = 16.dp),
    ) {
        Row {
            Icon(
                imageVector = iconImageVector,
                contentDescription = null,
                modifier = Modifier.rotate(iconRotate),
            )
            Spacer(Modifier.width(4.dp))

            val attributeNumberChange = attributeNumber / transformAttribute

            Text(
                text = "$attributeNumberChange $attributeText",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
        }

        Spacer(Modifier.height(4.dp))

        Text(
            text = attributeName,
            color = MaterialTheme.colorScheme.onBackground.copy(ALPHA_TEXT),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
