package com.architects.pokearch.ui.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(val baseRoute: String, navArgs: List<NavArg> = emptyList()) {

    val route = run {
        val args = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(args)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

    object Main : NavItem("mainScreen")

    object Home : NavItem("homeScreen")

    object Team : NavItem("teamScreen")

    object Detail : NavItem("detailScreen", listOf(NavArg.PokemonId)) {
        fun createRoute(pokemonId: Int): String = "$baseRoute/$pokemonId"
    }

}

sealed class NavArg(val key: String, val navTypeWrapper: NavTypeWrapper) {

    val navType = navTypeWrapper.navType

    inline fun <reified T> getArg(navBackStackEntry: NavBackStackEntry): T =
        navTypeWrapper.getArg(navBackStackEntry, key) as T

    object PokemonId : NavArg("pokemonId", NavTypeWrapper.IntType)
}

sealed class NavTypeWrapper(val navType: NavType<*>) {
    abstract fun getArg(navBackStackEntry: NavBackStackEntry, key: String): Any

    object IntType : NavTypeWrapper(NavType.IntType) {
        override fun getArg(navBackStackEntry: NavBackStackEntry, key: String): Any =
            navBackStackEntry.arguments?.getInt(key) as Any
    }

}
