package com.architects.pokearch.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import com.architects.pokearch.ui.components.bottombar.ArchBottomNavigationBar
import com.architects.pokearch.ui.components.topAppBar.ArchTopAppBar
import com.architects.pokearch.ui.components.topAppBar.ArchTopAppBarType
import com.architects.pokearch.ui.navigation.Navigation
import com.architects.pokearch.ui.theme.PokeArchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokeArchApp(
    modifier: Modifier = Modifier,
    stateApp: PokeArchAppState = rememberPokeArchAppState(),
) {
    PokeArchScreen {
        Scaffold(
            modifier = modifier
                .nestedScroll(stateApp.scrollBehavior.nestedScrollConnection)
                .fillMaxWidth()
                .height(56.dp),
            topBar = {
                AnimatedVisibility(
                    visible = stateApp.showTopBar != ArchTopAppBarType.NONE,
                    enter = fadeIn() + expandVertically(expandFrom = Alignment.Top),
                    exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut(),
                ) {
                    when (stateApp.showTopBar) {
                        ArchTopAppBarType.NORMAL, ArchTopAppBarType.NONE -> ArchTopAppBar(
                            archTopAppBarType = stateApp.showTopBar,
                            onBackButtonClicked = {},
                            onTextChange = {}
                        )

                        ArchTopAppBarType.SEARCH -> ArchTopAppBar(
                            text = stateApp.searchText.value,
                            scrollBehavior = stateApp.scrollBehavior,
                            archTopAppBarType = stateApp.showTopBar,
                            onBackButtonClicked = { stateApp.searchText.value = "" },
                            onTextChange = { stateApp.searchText.value = it }
                        )
                    }
                }
            },
            bottomBar = {
                ArchBottomNavigationBar(
                    isBottomBarVisible = stateApp.showBottomBar,
                    navController = stateApp.navHostController
                )
            },
            content = { padding ->
                Box(modifier = Modifier.padding(padding)) {
                    Navigation(
                        navHostController = stateApp.navHostController,
                        pokemonName = stateApp.searchText.value,
                        onNavigationDetailClick = { stateApp.navToDetail(it) }
                    )
                }
            }
        )

    }
}
