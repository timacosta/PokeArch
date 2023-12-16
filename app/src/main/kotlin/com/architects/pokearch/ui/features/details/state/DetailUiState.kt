package com.architects.pokearch.ui.features.details.state

import com.architects.pokearch.core.domain.model.PokemonInfo

sealed class DetailUiState {
    data object Loading : DetailUiState()
    data object Error : DetailUiState()
    data class Success(val pokemonInfo: PokemonInfo) : DetailUiState()
}
