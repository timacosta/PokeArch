package com.architects.pokearch.ui.components.topAppBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.architects.pokearch.R

@Composable
fun ArchSearchBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    Surface(
        modifier = Modifier.height(64.dp),
        shadowElevation = 3.dp,
        color = MaterialTheme.colorScheme.primary, //TODO: Change color with animation
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = {
                onTextChange(it)
            },
            placeholder = {
                Text(
                    modifier = Modifier
                        .alpha(0.5f),
                    text = stringResource(R.string.search_label_place_holder),
                    color = MaterialTheme.colorScheme.onPrimary
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
                TrailingIconSearchBar(text, onTextChange)
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchClicked(text)
                }
            ),
            colors = textFieldColors(),
        )
    }

}

@Composable
private fun TrailingIconSearchBar(text: String, onTextChange: (String) -> Unit) {
    if (text.isNotEmpty()) {
        Icon(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp)
                .alpha(0.5f)
                .clickable {
                    if (text.isNotEmpty()) {
                        onTextChange("")
                    }
                },
            imageVector = Icons.Default.Clear,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Composable
private fun textFieldColors() = TextFieldDefaults.colors(
    focusedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
    unfocusedTextColor = MaterialTheme.colorScheme.inversePrimary.copy(alpha = 0.8f),
    focusedContainerColor = MaterialTheme.colorScheme.primary,
    unfocusedContainerColor = MaterialTheme.colorScheme.primary,
    disabledContainerColor = MaterialTheme.colorScheme.primary,
    cursorColor = MaterialTheme.colorScheme.inversePrimary,
    selectionColors = TextSelectionColors(
        handleColor = MaterialTheme.colorScheme.inversePrimary,
        backgroundColor = Color.Transparent
    )
)

@Composable
private fun LeadingIconSearchBar(onCloseClicked: () -> Unit) {
    IconButton(
        modifier = Modifier
            .alpha(0.5f)
            .padding(start = 16.dp, end = 16.dp)
            .size(24.dp),
        onClick = {
            onCloseClicked()
        }
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ArchSearchBarPreview() {
    ArchSearchBar(
        onCloseClicked = {},
        onSearchClicked = {},
        onTextChange = {},
        text = "Some text"
    )

}



