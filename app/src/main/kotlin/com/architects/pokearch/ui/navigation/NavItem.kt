package com.architects.pokearch.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(val baseRoute: String, navArgs: List<NavArg> = emptyList()){

    val route = run {
        val args = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(args)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key){ type = it.navType }
    }

    object Main: NavItem("mainScreen")

    object Home: NavItem("homeScreen")

    object Team: NavItem("teamScreen")

    object Detail: NavItem("detailScreen", listOf(NavArg.PokemonId)){
        fun createRoute(pokemonId: Int):String = "$baseRoute/$pokemonId"
    }

}

enum class NavArg(val key: String, val navType: NavType<*>){
    PokemonId("pokemonId", NavType.IntType)
}
