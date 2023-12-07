package com.architects.pokearch.ui.home.state

import com.architects.pokearch.core.domain.model.Pokemon

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data object Error : HomeUiState()
    data class Success(val networkPokemonList: List<Pokemon>) : HomeUiState()
}
