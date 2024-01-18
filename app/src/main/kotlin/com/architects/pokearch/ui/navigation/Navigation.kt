package com.architects.pokearch.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.architects.pokearch.ui.features.details.ui.DetailScreen
import com.architects.pokearch.ui.features.home.ui.HomeScreen
import com.architects.pokearch.ui.features.shakeNCatch.ui.ShakeNCatchScreen
import com.architects.pokearch.ui.features.team.ui.TeamScreen

@RequiresApi(Build.VERSION_CODES.P)
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
        teamNav(onNavigationDetailClick)
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


fun NavGraphBuilder.teamNav(
    onNavigationDetailClick: (String) -> Unit,
){
    navigation(
        route = Feature.TEAM.route,
        startDestination = NavCommand.ContentType(Feature.TEAM).route
    ){
        composable(NavCommand.ContentType(Feature.TEAM)){
            TeamScreen(
                onNavigationClick = { pokemonId ->
                    onNavigationDetailClick(
                        NavCommand.ContentDetail(Feature.MAIN).createRoute(pokemonId)
                    )
                }
            )
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
            ShakeNCatchScreen(
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
