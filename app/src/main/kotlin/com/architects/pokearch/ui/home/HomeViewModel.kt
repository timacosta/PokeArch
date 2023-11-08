package com.architects.pokearch.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.di.IO
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.ui.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryContract: PokeArchRepositoryContract,
    @IO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            withContext(dispatcher) {
                getPokemonList()
            }
        }
    }

    private suspend fun getPokemonList() {
        repositoryContract.fetchPokemonList().collectLatest { result ->
            result.fold(
                ifLeft = {
                    _uiState.value = HomeUiState.Error
                },
                ifRight = { pokemonList ->
                    _uiState.value = HomeUiState.Success(pokemonList)
                }
            )
        }
    }
}