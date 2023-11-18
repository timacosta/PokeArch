package com.architects.pokearch.ui.components.topAppBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.architects.pokearch.ui.main.state.SearchWidgetState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchMainAppTopBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    scrollBehavior: TopAppBarScrollBehavior,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggeredClicked: () -> Unit,
) {
        when (searchWidgetState) {
            SearchWidgetState.CLOSED -> {
                ArchTopAppBar(
                    onSearchClicked = onSearchTriggeredClicked,
                    scrollBehavior = scrollBehavior,
                )
            }

            SearchWidgetState.OPENED -> {
                ArchSearchBar(
                    text = searchTextState,
                    onTextChange = onTextChange,
                    onCloseClicked = onCloseClicked,
                    onSearchClicked = onSearchClicked
                )
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ArchMainAppTopBarPreviewClosed(
) {
    ArchMainAppTopBar(
        searchWidgetState = SearchWidgetState.CLOSED,
        searchTextState = "",
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {},
        onSearchTriggeredClicked = {}
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ArchMainAppTopBarPreviewOpened(
) {
    ArchMainAppTopBar(
        searchWidgetState = SearchWidgetState.OPENED,
        searchTextState = "",
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {},
        onSearchTriggeredClicked = {}
    )
}


