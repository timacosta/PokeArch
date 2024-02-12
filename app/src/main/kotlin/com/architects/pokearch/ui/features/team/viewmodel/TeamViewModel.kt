package com.architects.pokearch.ui.features.team.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.ui.features.team.state.TeamUiState
import com.architects.pokearch.usecases.GetPokemonTeam
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamViewModel @Inject constructor(
    getPokemonTeam: GetPokemonTeam
) : ViewModel() {

    private val _uiState: MutableStateFlow<TeamUiState> = MutableStateFlow(TeamUiState.Success())
    val uiState: StateFlow<TeamUiState> = _uiState

    init {
        viewModelScope.launch {

            getPokemonTeam()
                .onStart {
                    _uiState.update { TeamUiState.Loading }
                }
                .onEmpty {
                    _uiState.update { TeamUiState.Success() }
                }
                .collect { pokemonTeam ->
                    _uiState.update {
                        TeamUiState.Success(pokemonTeam)
                    }
                }
        }
    }
}
