package com.architects.pokearch.ui.components.bottombar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import com.architects.pokearch.ui.navigation.NavItem

@Composable
fun ArchBottomNavigationBar(
    isBottomBarVisible: Boolean,
    navController: NavController,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primary
) {

    if (isBottomBarVisible) {
        NavigationBar(
            modifier = modifier.fillMaxWidth(),
            contentColor = contentColor,
            containerColor = containerColor
        ) {

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            NavItem.entries.forEach { item ->
                val title = stringResource(id = item.title)
                val isCurrentRoute = currentRoute?.contains(item.navCommand.route)

                NavigationBarItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = title
                        )
                    },
                    label = { Text(text = title) },
                    selected = isCurrentRoute == true,
                    onClick = {
                        navController.navigate(item.navCommand.route) {
                            // TODO: navigateToTopLevelDestination(navController)
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                        unselectedTextColor = MaterialTheme.colorScheme.inversePrimary,
                        selectedTextColor = MaterialTheme.colorScheme.onSecondary
                    )
                )
            }
        }
    }
}

private fun NavOptionsBuilder.navigateToTopLevelDestination(navController: NavController) {
    navController.graph.startDestinationRoute?.let { route ->
        popUpTo(route) {
            saveState = true
        }
    }
    launchSingleTop = true
    restoreState = true
}
