package com.architects.pokearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.architects.pokearch.ui.screens.detail.DetailScreen
import com.architects.pokearch.ui.main.MainScreen

@Composable
fun Navigation(){
    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = NavCommand.Main.route
    ){
        composable(NavCommand.Main){
            MainScreen { pokemonId ->
                navHostController.navigate(NavCommand.Detail.createRoute(pokemonId))
            }
        }
        composable(NavCommand.Detail){ backStackEntry ->
            DetailScreen(NavArg.PokemonId.getArg(backStackEntry))
        }
    }

}

fun NavGraphBuilder.composable(
    navItem: NavCommand,
    content: @Composable (NavBackStackEntry) -> Unit
){
    composable(
        route = navItem.route,
        arguments = navItem.args
    ){
        content(it)
    }
}
