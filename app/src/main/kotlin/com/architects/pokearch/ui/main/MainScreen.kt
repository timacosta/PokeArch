package com.architects.pokearch.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.architects.pokearch.ui.navigation.MainNavigation

@Composable
fun MainScreen(onNavigationDetailClick:(pokemonId: Int) -> Unit){
    val navHostController = rememberNavController()

    /*TODO: Cambiar box por scaffold con bottom bar*/
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "MainScreen (Scaffold)")
        MainNavigation(
            navHostController = navHostController,
            onNavigationDetailClick = onNavigationDetailClick
        )
    }
}