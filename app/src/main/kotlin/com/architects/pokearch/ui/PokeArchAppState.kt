package com.architects.pokearch.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.architects.pokearch.ui.components.topAppBar.ArchTopAppBarType
import com.architects.pokearch.ui.navigation.Feature
import com.architects.pokearch.ui.navigation.SubRoute


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun rememberPokeArchAppState(
    navHostController: NavHostController = rememberNavController(),
    topAppBarState: TopAppBarState = rememberTopAppBarState(),
    searchText: MutableState<String> = remember { mutableStateOf("") },
): PokeArchAppState =
    remember(navHostController, topAppBarState, searchText) {
        PokeArchAppState(navHostController, topAppBarState, searchText)
    }

@OptIn(ExperimentalMaterial3Api::class)
class PokeArchAppState(
    val navHostController: NavHostController,
    private val topAppBarState: TopAppBarState,
    val searchText: MutableState<String>,
) {
    val scrollBehavior
        @Composable get() = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    private val currentRoute: String
        @Composable get() =
            navHostController.currentBackStackEntryAsState().value?.destination?.route.orEmpty()

    val showTopBar: ArchTopAppBarType
        @Composable get() = getShowTopBar(currentRoute)

    val showBottomBar: Boolean
        @Composable get() = getShowBottomBar(currentRoute)

    fun navToDetail(route: String) = navHostController.navigate(route)
}

fun getShowTopBar(current: String) =
    when {
        current.contains(Feature.MAIN.route) &&
                current.contains(SubRoute.HOME.route) ->
            ArchTopAppBarType.SEARCH

        current.contains(SubRoute.DETAIL.route) ->
            ArchTopAppBarType.NONE

        else -> ArchTopAppBarType.NORMAL
    }

fun getShowBottomBar(current: String) =
    when {
        current.contains(SubRoute.DETAIL.route) ->
            false

        else -> true
    }
