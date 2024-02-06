package com.architects.pokearch.ui.components.bottombar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
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
import androidx.navigation.compose.currentBackStackEntryAsState
import com.architects.pokearch.ui.navigation.NavItem

@Composable
fun ArchBottomNavigationBar(
    isBottomBarVisible: Boolean,
    navController: NavController,
    modifier: Modifier = Modifier,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    containerColor: Color = MaterialTheme.colorScheme.primary,
) {

    AnimatedVisibility(
        visible = isBottomBarVisible,
        enter = fadeIn() + expandVertically(),
        exit = shrinkVertically() + fadeOut(),
    ) {
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
                            popUpTo(item.navCommand.route) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        unselectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                        unselectedTextColor = MaterialTheme.colorScheme.inversePrimary,
                        selectedIconColor = MaterialTheme.colorScheme.tertiary,
                        selectedTextColor = MaterialTheme.colorScheme.tertiary
                    )
                )
            }
        }
    }
}
