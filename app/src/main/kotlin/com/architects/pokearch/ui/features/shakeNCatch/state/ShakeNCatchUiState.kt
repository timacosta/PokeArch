package com.architects.pokearch.ui.features.shakeNCatch.state

import com.architects.pokearch.core.domain.model.PokemonInfo

data class ShakeNCatchUiState(
    val openedPokeball: Boolean = false,
    val isLoading: Boolean = true,
    val error: Boolean = false,
    val pokemonInfo: PokemonInfo? = null,
)
