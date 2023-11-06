package com.architects.pokearch.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.architects.pokearch.R

enum class NavItem(
    val navCommand: NavCommand,
    val icon: ImageVector,
    @StringRes val title: Int
) {
    HOME(NavCommand.Home, Icons.Default.Home, R.string.home_title),
    TEAM(NavCommand.Team, Icons.Default.Star, R.string.team_title),
    FEATURE(NavCommand.Feature, Icons.Default.Face, R.string.feature_title)
}
sealed class NavCommand(val baseRoute: String, navArgs: List<NavArg> = emptyList()) {

    val route = run {
        val args = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(args)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }

    data object Main : NavCommand("mainScreen")

    data object Home : NavCommand("homeScreen")

    data object Team : NavCommand("teamScreen")

    data object Feature : NavCommand("featureScreen")

    data object Detail : NavCommand("detailScreen", listOf(NavArg.PokemonId)) {
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
