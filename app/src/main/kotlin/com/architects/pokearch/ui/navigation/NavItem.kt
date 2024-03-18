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
    HOME(NavCommand.ContentType(Feature.MAIN), Icons.Default.Home, R.string.home_title),
    TEAM(NavCommand.ContentType(Feature.TEAM), Icons.Default.Star, R.string.team_title),
    FEATURE(NavCommand.ContentType(Feature.RANDOM), Icons.Default.Face, R.string.feature_title)
}

sealed class NavCommand(
    internal val feature: Feature,
    internal val subRoute: String = SubRoute.HOME.route,
    navArgs: List<NavArg> = emptyList(),
) {

    class ContentType(feature: Feature) : NavCommand(feature)

    class ContentDetail(feature: Feature):
        NavCommand(feature, SubRoute.DETAIL.route, listOf(NavArg.PokemonId)){
            fun createRoute(pokemonId: Int) = "${feature.route}/$subRoute/$pokemonId"
        }

    val route = run {
        val args = navArgs.map { "{${it.key}}" }
        listOf(feature.route)
            .plus(subRoute)
            .plus(args)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }
}
sealed class NavArg(val key: String, val navTypeWrapper: NavTypeWrapper) {

    val navType = navTypeWrapper.navType

    inline fun <reified T> getArg(navBackStackEntry: NavBackStackEntry): T =
        navTypeWrapper.getArg(navBackStackEntry, key) as T

    data object PokemonId : NavArg("pokemonId", NavTypeWrapper.IntType)
}

sealed class NavTypeWrapper(val navType: NavType<*>) {
    abstract fun getArg(navBackStackEntry: NavBackStackEntry, key: String): Any

    data object IntType : NavTypeWrapper(NavType.IntType) {
        override fun getArg(navBackStackEntry: NavBackStackEntry, key: String): Any =
            navBackStackEntry.arguments?.getInt(key) as Any
    }

}
