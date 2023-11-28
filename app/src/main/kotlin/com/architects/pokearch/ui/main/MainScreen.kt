package com.architects.pokearch.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.architects.pokearch.ui.components.bottombar.ArchBottomNavigationBar
import com.architects.pokearch.ui.components.topAppBar.ArchTopAppBar
import com.architects.pokearch.ui.navigation.MainNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onItemClick: (pokemonId: Int) -> Unit
) {
    val navHostController = rememberNavController()

    val state by mainViewModel.uiState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection)
            .fillMaxWidth()
            .height(56.dp),
        topBar = {
            ArchTopAppBar(
                text = state.searchText,
                scrollBehavior = scrollBehavior,
                isTopBarVisible = state.isTopBarVisible,
                onTextChange = mainViewModel::updateSearchTextState
            )
        },
        bottomBar = {
            ArchBottomNavigationBar(
                isBottomBarVisible = state.isBottomBarVisible,
                navController = navHostController
            )
        },
        content = { padding ->

            MainContent(
                modifier = Modifier,
                pokemonName = state.searchText,
                padding = padding,
                navHostController = navHostController,
                onItemClick = onItemClick
            )
        }
    )
}

@Composable
private fun MainContent(
    pokemonName: String,
    padding: PaddingValues,
    navHostController: NavHostController,
    onItemClick: (pokemonId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier.padding(padding)) {
        MainNavHost(
            pokemonName = pokemonName,
            navHostController = navHostController,
            onNavigationDetailClick = onItemClick,
        )
    }
}
