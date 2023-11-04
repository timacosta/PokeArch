package com.architects.pokearch.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.architects.pokearch.ui.home.state.HomeUiState

@Composable
fun HomeScreen(onNavigationClick: (Int) -> Unit) {

    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        when(val state = uiState) {
            is HomeUiState.Loading -> {

            }

            is HomeUiState.Success -> {
                LazyColumn {
                    items(state.pokemonList) {
                        Text(text = it.name)
                    }
                }
            }
            
            is HomeUiState.Error ->  {
                Text(text = "Something went wrong")
            }
        }
    }
}