package com.architects.pokearch.ui.components.topAppBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.architects.pokearch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchSearchBar(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit = {},
    onSearchClicked: (String) -> Unit = {}
) {
    var text by remember { mutableStateOf("") }

    SearchBar(
        modifier = modifier
            .height(56.dp)
            .padding(start = 16.dp, end = 16.dp)
            .fillMaxWidth(1f)
            .wrapContentHeight(),
        query = text,
        onQueryChange = { text = it },
        onSearch = {
            onSearchClicked(text)
        },
        active = false,
        onActiveChange = {},
        placeholder = {
            Text(
                text = stringResource(R.string.search_label_place_holder),
                color = Color.Black.copy(alpha = 0.5f)
            )
        },
        leadingIcon = {
            IconButton(
                modifier = modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .size(24.dp),
                onClick = {
                    onCloseClicked()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
        },
        trailingIcon = {
            TrailingIcon(text = text){
                text = ""
            }
        },
        shape = SearchBarDefaults.inputFieldShape,
        colors = searchBarColors()
    ) { }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun searchBarColors() = SearchBarDefaults.colors(
    containerColor = MaterialTheme.colorScheme.surface,
    dividerColor = Color.LightGray,
    inputFieldColors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f),
        unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
        cursorColor = MaterialTheme.colorScheme.inversePrimary,
        selectionColors = TextSelectionColors(
            handleColor = Color.White,
            backgroundColor = Color.Transparent
        ),
        focusedLeadingIconColor = MaterialTheme.colorScheme.inversePrimary,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.inversePrimary,
        focusedTrailingIconColor = MaterialTheme.colorScheme.inversePrimary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.inversePrimary
    )
)

@Composable
private fun TrailingIcon(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    if (text.isNotEmpty()) {
        Icon(
            modifier = modifier
                .padding(start = 16.dp, end = 16.dp)
                .clickable {
                    if (text.isNotEmpty()) {
                        onClick()
                    }
                },
            imageVector = Icons.Default.Clear,
            contentDescription = null
        )
    }
}

@Preview(showBackground = true, heightDp = 300)
@Composable
private fun MySearchBarPreview() {
    ArchSearchBar(
        onCloseClicked = {},
        onSearchClicked = {}
    )

}


