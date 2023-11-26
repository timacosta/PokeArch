package com.architects.pokearch.ui.components.topAppBar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.architects.pokearch.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArchTopAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    modifier: Modifier = Modifier,
) {
    var isExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            if (!isExpanded) {
                Row(
                    modifier = modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        modifier = Modifier.align(Alignment.CenterVertically),
                    )
                    IconButton(modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 16.dp),
                        onClick = {
                            isExpanded = !isExpanded
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

        },
        actions = {
            if (isExpanded) {
                ArchSearchTextField(text = text, onTextChange = onTextChange) {
                    isExpanded = !isExpanded
                }
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

    Box(modifier = Modifier.height(56.dp)) {

        ArchTopAppBar(
            text = "",
            onTextChange = {},
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        )
    }

}
