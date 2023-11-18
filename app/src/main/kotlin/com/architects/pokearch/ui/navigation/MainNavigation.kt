package com.architects.pokearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.architects.pokearch.ui.feature.FeatureScreen
import com.architects.pokearch.ui.home.HomeScreen
import com.architects.pokearch.ui.team.TeamScreen

@Composable
fun MainNavHost(
    navHostController: NavHostController,
    onNavigationDetailClick: (Int) -> Unit,
) {

    NavHost(
        navController = navHostController,
        route = NavCommand.Main.route,
        startDestination = NavCommand.Home.route
    ) {
        composable(NavCommand.Home) {
            HomeScreen(
                onNavigationClick = { pokemonId ->
                    onNavigationDetailClick(pokemonId)
                }
            )
        }
        composable(NavCommand.Team) {
            TeamScreen()
        }

        composable(NavCommand.Feature) {
            FeatureScreen()
        }
    }
}


