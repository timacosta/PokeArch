package com.architects.pokearch.ui.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.di.IO
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.ui.details.state.DetailUiState
import com.architects.pokearch.ui.navigation.NavArg
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repositoryContract: PokeArchRepositoryContract,
    @IO val dispatcher: CoroutineDispatcher,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonId = savedStateHandle.get<Int>(NavArg.PokemonId.key) ?: 0

    private val _pokemonDetailInfo: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState.Loading)
    val pokemonDetailInfo: MutableStateFlow<DetailUiState> = _pokemonDetailInfo

    init {
        viewModelScope.launch(dispatcher) {
            getPokemonDetails(pokemonId)
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

    fun getCryUrl(cry: suspend (String) -> Unit) {
        viewModelScope.launch(dispatcher) {
            with(_pokemonDetailInfo.value) {
                if (this is DetailUiState.Success) {
                    cry(repositoryContract.fetchCry(pokemonInfo.name))
                }
            }
        }
    }


}
