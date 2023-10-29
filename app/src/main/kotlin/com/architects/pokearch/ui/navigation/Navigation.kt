package com.architects.pokearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.architects.pokearch.ui.detail.DetailScreen
import com.architects.pokearch.ui.main.MainScreen

@Composable
fun Navigation(){
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = NavItem.Main.route
    ){
        composable(NavItem.Main){
            MainScreen { pokemonId ->
                navHostController.navigate(NavItem.Detail.createRoute(pokemonId))
            }
        }
        composable(NavItem.Detail){ backStackEntry ->
            DetailScreen(NavArg.PokemonId.getArg(backStackEntry))
        }
    }

}

fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
){
    composable(
        route = navItem.route,
        arguments = navItem.args
    ){
        content(it)
    }
}