package com.architects.pokearch.ui.feature

import com.architects.pokearch.core.model.PokemonInfo

data class FeatureUiState(
    val openedPokeball: Boolean = false,
    val isLoading: Boolean = true,
    val error: Boolean = false,
    val pokemonInfo: PokemonInfo? = null,
)
