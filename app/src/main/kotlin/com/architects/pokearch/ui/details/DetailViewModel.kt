package com.architects.pokearch.ui.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.di.IO
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.core.model.Pokemon
import com.architects.pokearch.ui.details.state.DetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repositoryContract: PokeArchRepositoryContract,
    @IO private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val pokemonIdArgs: MutableSharedFlow<Int> = MutableSharedFlow(replay = 1)

    private val _pokemonDetailInfo: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState.Loading)
    val pokemonDetailInfo: MutableStateFlow<DetailUiState> = _pokemonDetailInfo

    init {
        viewModelScope.launch(dispatcher) {
            pokemonIdArgs.collectLatest { pokemonId ->
                getPokemonDetails(pokemonId)
            }
        }
    }

    private suspend fun getPokemonDetails(pokemonId: Int) {
        repositoryContract.fetchPokemonInfo(pokemonId).collectLatest { result ->
            result.fold(
                ifLeft = {
                    _pokemonDetailInfo.value = DetailUiState.Error
                },
                ifRight = { pokemonInfo ->
                    _pokemonDetailInfo.value = DetailUiState.Success(pokemonInfo)
                }
            )
        }
    }

    fun getCurrentPokemonId(id: Int) = pokemonIdArgs.tryEmit(id)
}
