package com.architects.pokearch.ui.main

import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
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
                modifier = modifier,
                padding = padding,
                navHostController = navHostController,
                onItemClick = onItemClick
            )
        }
    )
}

@Composable
private fun MainContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    navHostController: NavHostController,
    onItemClick: (pokemonId: Int) -> Unit
) {
    Box(modifier.padding(padding)) {
        MainNavHost(
            navHostController = navHostController,
            onNavigationDetailClick = onItemClick
        )
    }
}
