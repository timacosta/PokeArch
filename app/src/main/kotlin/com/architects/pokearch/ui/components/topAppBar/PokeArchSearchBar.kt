package com.architects.pokearch.ui.components.topAppBar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.ui.draw.scale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeArchSearchBar(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit = {},
    onSearch: (String) -> Unit = {}
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
            onSearch(text)
        },
        active = false,
        onActiveChange = {},
        placeholder = {
            Text(
                text = "Search...",
                color = Color.Black.copy(alpha = 0.5f)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        trailingIcon = {
                Icon(
                    modifier = Modifier
                        .scale(0.7f)
                        .clickable {
                                   when {
                                       text.isNotEmpty() -> text = ""
                                       else -> onCloseClicked()
                                   }
                        },
                    imageVector = Icons.Default.Clear,
                    contentDescription = null
                )
        },
        shape = SearchBarDefaults.inputFieldShape,
        colors = SearchBarDefaults.colors(
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
    ) { }
}

@Preview(showBackground = true, heightDp = 300)
@Composable
fun MySearchBarPreview() {
    PokeArchSearchBar(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    )

}



