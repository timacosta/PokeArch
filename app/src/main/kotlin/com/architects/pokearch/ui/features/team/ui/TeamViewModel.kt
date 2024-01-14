package com.architects.pokearch.ui.features.team.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.architects.pokearch.core.di.annotations.IO
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.ui.components.pagingsource.PokemonPagingSource
import com.architects.pokearch.usecases.FetchPokemonList
import com.architects.pokearch.usecases.GetPokemonList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

sealed class TeamUiState {
    data object Loading : TeamUiState()
    data class Error(val failure: Failure) : TeamUiState()
    data class Success(val pokemonList: Flow<PagingData<Pokemon>>) : TeamUiState()
}

@HiltViewModel
class TeamViewModel @Inject constructor(
    private val getPokemonList: GetPokemonList,
    private val fetchPokemonList: FetchPokemonList,
    @IO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState: MutableStateFlow<TeamUiState> = MutableStateFlow(TeamUiState.Loading)
    val uiState: StateFlow<TeamUiState> = _uiState

    init {
        viewModelScope.launch {
            when (val failure = withContext(dispatcher) { fetchPokemonList() }) {
                null -> getPokemonList()
                else -> _uiState.value = TeamUiState.Error(failure)
            }
        }
    }

    fun getPokemonList(pokemonName: String = "") {
        _uiState.value =
            TeamUiState.Success(
                PokemonPagingSource.getPager(pokemonName, getPokemonList, viewModelScope)
            )
    }
}
