package com.architects.pokearch.ui.components.topAppBar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.architects.pokearch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchTopAppBar(
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit,
    onSearchbarIsExpanded: (Boolean) -> Unit,
    searchBarIsExpandedState: Boolean
) {
        TopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.app_name)
                )
            },
            actions = {

                IconButton(modifier = Modifier
                    .padding(end = 16.dp),
                    onClick = {
                        onSearchClicked()
                        onSearchbarIsExpanded(searchBarIsExpandedState)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            modifier = modifier,
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            ),
            scrollBehavior = scrollBehavior
        )
    }


@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
private fun ArchTopAppBarPreview() {

    Box(modifier = Modifier.height(56.dp)){

        ArchTopAppBar(
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            onSearchClicked = { },
            onSearchbarIsExpanded = { },
            searchBarIsExpandedState = false
        )
    }

}
