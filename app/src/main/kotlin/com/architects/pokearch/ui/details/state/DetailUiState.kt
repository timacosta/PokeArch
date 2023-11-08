package com.architects.pokearch.ui.details.state

import com.architects.pokearch.core.model.PokemonInfo

sealed class DetailUiState {
    data object Loading : DetailUiState()
    data object Error : DetailUiState()
    data class Success(val pokemonInfo: PokemonInfo) : DetailUiState()
}
