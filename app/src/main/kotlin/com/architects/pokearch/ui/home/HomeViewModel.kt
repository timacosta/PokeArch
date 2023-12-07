package com.architects.pokearch.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.di.IO
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.ui.home.state.HomeUiState
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

    private var cumulativePokemonList: MutableList<Pokemon> = mutableListOf()
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
                        cumulativePokemonList.addAll(pokemonList)
                        _uiState.value = HomeUiState.Success(cumulativePokemonList)
                    }
                )
            }
        }

    fun onLoadMore(pokemonName: String) {
        currentPage++
        viewModelScope.launch(dispatcher) {
            getPokemonList(pokemonName, currentPage)
        }
    }
}
