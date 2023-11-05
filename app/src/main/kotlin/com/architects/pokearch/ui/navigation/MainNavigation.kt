package com.architects.pokearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.architects.pokearch.ui.home.HomeScreen
import com.architects.pokearch.ui.team.TeamScreen

@Composable
fun MainNavigation(navHostController: NavHostController, onNavigationDetailClick:(Int) -> Unit) {

    NavHost(
        navController = navHostController,
        route = NavItem.Main.route,
        startDestination = NavItem.Home.route
    ){
        composable(NavItem.Home){
            HomeScreen { pokemonId ->
                onNavigationDetailClick(pokemonId)
            }
        }
        composable(NavItem.Team){
            TeamScreen()
        }
        /*TODO: Añadir pantallas de otras funciones*/
    }
}
