package com.architects.pokearch.ui.features.team.state

import androidx.compose.runtime.snapshots.SnapshotApplyResult
import com.architects.pokearch.domain.model.PokemonInfo

sealed class TeamUiState {
    data object Loading : TeamUiState()
    data class Error(val failure: SnapshotApplyResult.Failure) : TeamUiState()
    data class Success(val pokemonTeam: List<PokemonInfo> = emptyList()) : TeamUiState()
}
