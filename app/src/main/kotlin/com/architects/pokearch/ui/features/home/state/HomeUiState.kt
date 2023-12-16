package com.architects.pokearch.ui.features.home.state

import com.architects.pokearch.core.domain.model.Pokemon

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data object Error : HomeUiState()
    data class Success(val pokemonList: List<Pokemon>) : HomeUiState()
    data object NoSearchResult: HomeUiState()
}
