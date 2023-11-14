package com.architects.pokearch.ui.main

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.architects.pokearch.ui.components.bottombar.ArchBottomNavigationBar
import com.architects.pokearch.ui.main.state.SearchWidgetState
import com.architects.pokearch.ui.navigation.MainNavHost
import com.architects.pokearch.ui.components.topAppBar.ArchMainAppTopBar

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    onItemClick: (pokemonId: Int) -> Unit,
) {
    val navHostController = rememberNavController()

    val searchWidgetState by mainViewModel.searchWidgetState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            ArchMainAppTopBar(
                searchWidgetState =  searchWidgetState,
                onCloseClicked = { mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED) },
                onSearchTriggeredClicked = { mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED) },
                onSearchClicked = { Log.d("MainScreen", "onSearchClicked: $it")  })

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
