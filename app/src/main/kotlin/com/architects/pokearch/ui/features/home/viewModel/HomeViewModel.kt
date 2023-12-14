package com.architects.pokearch.ui.features.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.di.IO
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.ui.features.home.state.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repositoryContract: PokeArchRepositoryContract,
    @IO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private var allPokemons: MutableList<Pokemon> = mutableListOf()
    private var filteredPokemons: MutableList<Pokemon> = mutableListOf()

    private var currentFilter: String = ""

    private var currentPage = 0

    suspend fun getPokemonList(pokemonName: String, page: Int = currentPage) =
        viewModelScope.launch(dispatcher) {
            repositoryContract.fetchPokemonList(
                filter = pokemonName,
                page = page,
            ).collectLatest { result ->
                result.fold(
                    ifLeft = {
                        _uiState.value = HomeUiState.Error
                    },
                    ifRight = { pokemonList ->

                        handleFilterChange(pokemonName)

                        if (pokemonName.isNotBlank()) {
                            emitUiStateWithFilter(pokemonList)
                        } else {
                            emitUiStateWithoutFilter(pokemonList)
                        }
                    }
                )
            }
        }

    private fun emitUiStateWithoutFilter(pokemonList: List<Pokemon>) {
        allPokemons.addAll(pokemonList.filterNot { it in allPokemons })
        _uiState.value = HomeUiState.Success(allPokemons.toList())
    }

    private fun emitUiStateWithFilter(pokemonList: List<Pokemon>) {
        filteredPokemons.addAll(pokemonList.filterNot { it in filteredPokemons })
        _uiState.value = HomeUiState.Success(filteredPokemons.toList())

        if (filteredPokemons.isEmpty()) {
            _uiState.value = HomeUiState.NoSearchResult
        }
    }

    private fun handleFilterChange(pokemonName: String) {
        if (pokemonName != currentFilter) {
            currentFilter = pokemonName
            filteredPokemons.clear()
            currentPage = 0
        }
    }

    suspend fun onLoadMore() {
        currentPage++
        viewModelScope.launch(dispatcher) {
            getPokemonList(currentFilter, currentPage)
        }
    }
}
