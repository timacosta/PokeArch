package com.architects.pokearch.ui.features.details.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.di.annotations.IO
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.domain.repository.MediaPlayerRepositoryContract
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.ui.features.details.state.DetailUiState
import com.architects.pokearch.ui.mapper.DialogData
import com.architects.pokearch.ui.mapper.ErrorDialogManager
import com.architects.pokearch.ui.navigation.NavArg
import com.architects.pokearch.usecases.FetchCry
import com.architects.pokearch.usecases.FetchPokemonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchPokemonDetails: FetchPokemonDetails,
    private val fetchCry: FetchCry,
    private val mediaPlayerRepository: MediaPlayerRepositoryContract,
    @IO val dispatcher: CoroutineDispatcher,
    private val errorDialogManager: ErrorDialogManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val pokemonId = savedStateHandle.get<Int>(NavArg.PokemonId.key) ?: 0

    private val _pokemonDetailInfo: MutableStateFlow<DetailUiState> =
        MutableStateFlow(DetailUiState.Loading)
    val pokemonDetailInfo: MutableStateFlow<DetailUiState> = _pokemonDetailInfo

    private val _dialogState: MutableStateFlow<DialogData?> = MutableStateFlow(null)
    val dialogState: StateFlow<DialogData?> = _dialogState

    private var once = false

    init {
        viewModelScope.launch(dispatcher) {
            getPokemonDetails(pokemonId)
        }
    }

    private suspend fun getPokemonDetails(pokemonId: Int) {
        fetchPokemonDetails(pokemonId).collectLatest { result ->
            result.fold(
                ifLeft = { failure ->
                    _pokemonDetailInfo.value = DetailUiState.Error
                    if (failure is Failure.NetworkError) _dialogState.value = errorDialogManager.transform(
                        errorType = failure.errorType,
                        onDismiss = { _dialogState.update { null } }
                    )
                },
                ifRight = { pokemonInfo ->
                    _pokemonDetailInfo.value = DetailUiState.Success(pokemonInfo)
                }
            )
        }
    }

    fun playCry(){
        viewModelScope.launch(dispatcher) {
            with(_pokemonDetailInfo.value) {
                if (this is DetailUiState.Success && !once) {
                    //TODO: Move to usecase to avoid calling the repository here
                    mediaPlayerRepository.playCry(getCryUrl(pokemonInfo.name.replaceFirstChar { it.lowercase() }))
                    once = true
                }
            }
        }
    }

    private suspend fun getCryUrl(pokemonName: String): String =
        withContext(dispatcher) {
            fetchCry(pokemonName)
        }
}
