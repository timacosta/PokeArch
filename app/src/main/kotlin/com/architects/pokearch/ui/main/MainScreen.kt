package com.architects.pokearch.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.architects.pokearch.ui.components.bottombar.PokeArchBottomNavigationBar
import com.architects.pokearch.ui.navigation.MainNavHost

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onItemClick: (pokemonId: Int) -> Unit,
) {
    val navHostController = rememberNavController()

    Scaffold(
        bottomBar = {
            PokeArchBottomNavigationBar(navController = navHostController)
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
