package com.architects.pokearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.architects.pokearch.ui.screens.feature.FeatureScreen
import com.architects.pokearch.ui.screens.home.HomeScreen
import com.architects.pokearch.ui.screens.team.TeamScreen

@Composable
fun MainNavigation(navHostController: NavHostController, onNavigationDetailClick: (Int) -> Unit) {

    NavHost(
        navController = navHostController,
        route = NavCommand.Main.route,
        startDestination = NavCommand.Home.route
    ) {
        composable(NavCommand.Home) {
            HomeScreen { pokemonId ->
                onNavigationDetailClick(pokemonId)
            }
        }
        composable(NavCommand.Team) {
            TeamScreen()
        }

        composable(NavCommand.Feature) {
            FeatureScreen()
        }
    }
}

