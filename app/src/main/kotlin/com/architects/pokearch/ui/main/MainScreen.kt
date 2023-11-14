package com.architects.pokearch.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.architects.pokearch.ui.components.bottombar.ArchBottomNavigationBar
import com.architects.pokearch.ui.components.topAppBar.ArchMainAppTopBar
import com.architects.pokearch.ui.main.state.SearchWidgetState
import com.architects.pokearch.ui.navigation.MainNavHost

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    onItemClick: (pokemonId: Int) -> Unit,
) {
    val navHostController = rememberNavController()

    val searchWidgetState by mainViewModel.searchWidgetState.collectAsStateWithLifecycle()

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ArchMainAppTopBar(
                searchWidgetState =  searchWidgetState,
                scrollBehavior = scrollBehavior,
                onCloseClicked = { mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED) },
                onSearchTriggeredClicked = { mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED) },
                onSearchClicked = { Log.d("MainScreen", "onSearchClicked: $it")  } //TODO: Implement Logic to search Pokemon
            )
        },
        bottomBar = {
            ArchBottomNavigationBar(navController = navHostController)
        },
        content = { padding ->

            MainContent(
                modifier = Modifier,
                padding = padding,
                navHostController = navHostController,
                onItemClick = onItemClick
            )
        },
        modifier = modifier
    )
}

@Composable
private fun MainContent(
    padding: PaddingValues,
    navHostController: NavHostController,
    onItemClick: (pokemonId: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier.padding(padding)) {
        MainNavHost(
            navHostController = navHostController,
            onNavigationDetailClick = onItemClick
        )
    }
}
