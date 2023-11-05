package com.architects.pokearch.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.architects.pokearch.ui.navigation.BottomNavigationBar
import com.architects.pokearch.ui.navigation.MainNavigation

@Composable
fun MainScreen(onNavigationDetailClick:(pokemonId: Int) -> Unit){
    val navHostController = rememberNavController()


    Scaffold(
        topBar = {},
        bottomBar = {
            BottomNavigationBar(navController = navHostController)
        },
        content = { padding ->
            Box(modifier = Modifier.padding(padding)){
                MainNavigation(
                    navHostController = navHostController,
                    onNavigationDetailClick = onNavigationDetailClick
                )
            }
        }
    )
}