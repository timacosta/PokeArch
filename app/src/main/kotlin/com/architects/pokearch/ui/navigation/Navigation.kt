package com.architects.pokearch.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.architects.pokearch.ui.details.DetailScreen
import com.architects.pokearch.ui.feature.RandomCatchScreen
import com.architects.pokearch.ui.home.HomeScreen
import com.architects.pokearch.ui.team.TeamScreen

@Composable
fun Navigation(
    navHostController: NavHostController,
    pokemonName: String,
    onNavigationDetailClick: (String) -> Unit,
) {
    NavHost(
        navController = navHostController,
        startDestination = Feature.MAIN.route
    ){
        homeNav(pokemonName, onNavigationDetailClick)
        teamNav()
        randomCatchNav(onNavigationDetailClick)
    }

}

fun NavGraphBuilder.homeNav(
    pokemonName: String,
    onNavigationDetailClick: (String) -> Unit,
){
    navigation(
        route = Feature.MAIN.route,
        startDestination = NavCommand.ContentType(Feature.MAIN).route
    ){
        composable(NavCommand.ContentType(Feature.MAIN)){
            HomeScreen(
                pokemonName = pokemonName,
                onNavigationClick = { pokemonId ->
                    onNavigationDetailClick(
                        NavCommand.ContentDetail(Feature.MAIN).createRoute(pokemonId)
                    )
                }
            )
        }
        composable(NavCommand.ContentDetail(Feature.MAIN)){
            DetailScreen()
        }
    }
}

fun NavGraphBuilder.teamNav(){
    navigation(
        route = Feature.TEAM.route,
        startDestination = NavCommand.ContentType(Feature.TEAM).route
    ){
        composable(NavCommand.ContentType(Feature.TEAM)){
            TeamScreen()
        }
    }
}

fun NavGraphBuilder.randomCatchNav(
    onNavigationDetailClick: (String) -> Unit,
){
    navigation(
        route = Feature.RANDOM.route,
        startDestination = NavCommand.ContentType(Feature.RANDOM).route
    ){
        composable(NavCommand.ContentType(Feature.RANDOM)){
            RandomCatchScreen(
                onNavigationClick = { pokemonId ->
                    onNavigationDetailClick(
                        NavCommand.ContentDetail(Feature.RANDOM).createRoute(pokemonId)
                    )
                }
            )
        }
        composable(NavCommand.ContentDetail(Feature.RANDOM)){
            DetailScreen()
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
