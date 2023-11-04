package com.architects.pokearch.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.architects.pokearch.MainViewModel

// TODO: Revisar nomenclatura y String resources
@Composable
fun MyScaffold(vm: MainViewModel) {

    Scaffold(
        topBar = { MainTopAppBar(vm) },
        bottomBar = { MyBottomAppBar(vm) },
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            // TODO: Añadir la pantalla segun input del usuario.
            when (vm.screenIndex.value) {
                0 -> {
                    //* TODO: Navegar a la pantalla principal */
                }

                1 -> {
                    /* TODO: Navegar a la pantalla segun navegacion (Favoritos?) */
                }
                // 3 -> { /* TODO: Agregar las futuras pantallas */ }
            }
        }
    }
}

@Composable
fun MyBottomAppBar(vm: MainViewModel) {
    BottomAppBar {
        NavigationBarItem(
            selected = vm.screenIndex.value == 0,
            onClick = { vm.onIndexChange(0) },
            icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "Home") })

        NavigationBarItem(
            selected = vm.screenIndex.value == 1,
            onClick = { vm.onIndexChange(1) },
            icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "Favourites") }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainTopAppBar(vm: MainViewModel) {

    // TODO: Cambiar por String Resources!!
    val title = when (vm.screenIndex.value) {
        0 -> "Pokedex"
        1 -> "Favourites"
        else -> "" // TODO: Añadir pantallas futuras.
    }

    TopAppBar(
        title = { Text(text = title) }
    )
}
