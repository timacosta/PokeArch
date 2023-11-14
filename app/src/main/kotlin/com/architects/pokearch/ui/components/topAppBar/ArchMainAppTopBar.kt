package com.architects.pokearch.ui.components.topAppBar

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import com.architects.pokearch.ui.main.state.SearchWidgetState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchMainAppTopBar(
    searchWidgetState: SearchWidgetState,
    scrollBehavior: TopAppBarScrollBehavior,
    onCloseClicked: () -> Unit,
    onSearchTriggeredClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
) {
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            ArchTopAppBar(
                onSearchClicked = onSearchTriggeredClicked,
                scrollBehavior = scrollBehavior
            )
        }

        SearchWidgetState.OPENED -> {
            ArchSearchBar(
                onSearchClicked = onSearchClicked,
                onCloseClicked = onCloseClicked
            )
        }
    }

}
