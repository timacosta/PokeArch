package com.architects.pokearch.ui.components.topAppBar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.architects.pokearch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchTopAppBar(
    archTopAppBarType: ArchTopAppBarType,
    onBackButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = "",
    scrollBehavior: TopAppBarScrollBehavior? = null,
    onTextChange: ((String) -> Unit),
) {

    var isExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            if (!isExpanded) {
                Text(
                    text = stringResource(R.string.app_name),
                )
            }
        },
        actions = {
            if (archTopAppBarType == ArchTopAppBarType.SEARCH) {
                if (isExpanded) {
                    ArchTextField(
                        text = text,
                        onTextChange = onTextChange
                    ) {
                        onBackButtonClicked()
                        isExpanded = !isExpanded
                    }
                } else {
                    SearchIconButton {
                        isExpanded = !isExpanded
                    }
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onBackground
        ),
        modifier = modifier,
        scrollBehavior = scrollBehavior
    )
}


@Composable
private fun ArchTextField(
    text: String,
    onTextChange: (String) -> Unit,
    onBackButtonClicked: () -> Unit,
) {
    ArchSearchTextField(text = text, onTextChange = onTextChange) {
        onBackButtonClicked()
    }
}

@Composable
private fun RowScope.SearchIconButton(onClick: () -> Unit) {
    IconButton(
        modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(end = 16.dp),
        onClick = {
            onClick()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = null,
        )
    }
}
