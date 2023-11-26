package com.architects.pokearch.ui.components.topAppBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.architects.pokearch.ui.main.MainUiState.SearchWidgetState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchMainAppTopBar(
    searchWidgetState: SearchWidgetState,
    searchTextState: String,
    searchBarIsExpandedState: Boolean,
    scrollBehavior: TopAppBarScrollBehavior,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
    onSearchTriggeredClicked: () -> Unit,
    onSearchBarIsExpandedClicked: (Boolean) -> Unit
) {
        when (searchWidgetState) {
            SearchWidgetState.CLOSED -> {
                ArchTopAppBar(
                    onSearchClicked = onSearchTriggeredClicked,
                    scrollBehavior = scrollBehavior,
                    searchBarIsExpandedState = searchBarIsExpandedState,
                    onSearchbarIsExpanded = onSearchBarIsExpandedClicked

                )
            }

            SearchWidgetState.OPENED -> {
                ArchSearchBar(
                    text = searchTextState,
                    onTextChange = onTextChange,
                    onCloseClicked = onCloseClicked,
                    searchBarIsExpandedState = searchBarIsExpandedState,
                    onSearchBarIsExpanded = onSearchBarIsExpandedClicked
                )
            }
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ArchMainAppTopBarPreviewClosed(
) {
    ArchMainAppTopBar(
        searchWidgetState = SearchWidgetState.CLOSED,
        searchTextState = "",
        searchBarIsExpandedState = false,
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {},
        onSearchTriggeredClicked = {},
        onSearchBarIsExpandedClicked = {}
    )
}
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun ArchMainAppTopBarPreviewOpened(
) {
    ArchMainAppTopBar(
        searchWidgetState = SearchWidgetState.OPENED,
        searchTextState = "",
        searchBarIsExpandedState = false,
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {},
        onSearchTriggeredClicked = {},
        onSearchBarIsExpandedClicked = {}

    )
}


