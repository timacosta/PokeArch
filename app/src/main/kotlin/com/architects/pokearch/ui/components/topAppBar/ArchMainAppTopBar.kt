package com.architects.pokearch.ui.components.topAppBar

import androidx.compose.runtime.Composable
import com.architects.pokearch.ui.main.state.SearchWidgetState

@Composable
fun ArchMainAppTopBar(
    searchWidgetState: SearchWidgetState,
    onCloseClicked: () -> Unit,
    onSearchTriggeredClicked: () -> Unit,
    onSearchClicked: (String) -> Unit
){
    when (searchWidgetState) {
        SearchWidgetState.CLOSED -> {
            ArchTopAppBar(
                onSearchClicked = onSearchTriggeredClicked
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