package com.architects.pokearch.ui.components.topAppBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.architects.pokearch.R

private const val CONS_MEDIUM_ALPHA = 0.5f

@Composable
fun ArchSearchTextField(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp),
        value = text,
        onValueChange = {
            onTextChange(it)
        },
        placeholder = {
            Text(
                modifier = Modifier
                    .alpha(CONS_MEDIUM_ALPHA),
                text = stringResource(R.string.search_label_place_holder),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        textStyle = MaterialTheme.typography.bodyLarge,
        singleLine = true,
        leadingIcon = {
            LeadingIconSearchBar {
                onCloseClicked()
            }
        },
        trailingIcon = {
            if (text.isNotEmpty()) {
                TrailingIcon(onTextChange = onTextChange)
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        colors = textFieldColors(),
    )
}

@Composable
private fun TrailingIcon(onTextChange: (String) -> Unit) {

        Icon(
            modifier = Modifier
                .padding(start = 16.dp, end = 10.dp)
                .alpha(CONS_MEDIUM_ALPHA)
                .clickable {
                    onTextChange("")
                },
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }

@Composable
private fun textFieldColors() = TextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
    unfocusedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
    focusedContainerColor = MaterialTheme.colorScheme.background,
    unfocusedContainerColor = MaterialTheme.colorScheme.background,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledContainerColor = MaterialTheme.colorScheme.background,
    //cursorColor = MaterialTheme.colorScheme.inversePrimary,
    /*selectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.inversePrimary,
        backgroundColor = Color.Transparent
    )*/
)

@Composable
private fun LeadingIconSearchBar(onCloseClicked: () -> Unit) {
    IconButton(
        modifier = Modifier
            .alpha(CONS_MEDIUM_ALPHA)
            .padding(start = 16.dp, end = 16.dp)
            .size(24.dp),
        onClick = {
            onCloseClicked()
        }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
    }
}



