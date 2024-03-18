package com.architects.pokearch.ui.features.details.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.di.annotations.IO
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.ui.features.details.di.annotations.PokemonId
import com.architects.pokearch.ui.features.details.state.DetailUiState
import com.architects.pokearch.ui.features.details.state.DetailUiState.Error
import com.architects.pokearch.ui.features.details.state.DetailUiState.Loading
import com.architects.pokearch.ui.features.details.state.DetailUiState.Success
import com.architects.pokearch.ui.mapper.DialogData
import com.architects.pokearch.ui.mapper.ErrorDialogManager
import com.architects.pokearch.usecases.FetchCry
import com.architects.pokearch.usecases.FetchPokemonDetails
import com.architects.pokearch.usecases.PlayCry
import com.architects.pokearch.usecases.UpdatePokemonInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val fetchPokemonDetails: FetchPokemonDetails,
    private val fetchCry: FetchCry,
    private val playCry: PlayCry,
    private val updatePokemonInfo: UpdatePokemonInfo,
    private val errorDialogManager: ErrorDialogManager,
    @IO val dispatcher: CoroutineDispatcher,
    @PokemonId private val pokemonId: Int,
) : ViewModel() {

    private val _uiState: MutableStateFlow<DetailUiState> =
        MutableStateFlow(Loading)
    val uiState: StateFlow<DetailUiState> = _uiState.asStateFlow()

    private val _dialogState: MutableStateFlow<DialogData?> = MutableStateFlow(null)
    val dialogState: StateFlow<DialogData?> = _dialogState

    fun getPokemonDetails() {
        viewModelScope.launch(dispatcher) {
            _uiState.update { Loading }
            fetchPokemonDetails(pokemonId)
                .collectLatest { result ->
                    result.fold(
                        ifLeft = { failure ->
                            _uiState.update { Error }
                            if (failure is Failure.NetworkError) _dialogState.update {
                                errorDialogManager.transform(
                                    errorType = failure.errorType,
                                    onDismiss = { _dialogState.update { null } }
                                )
                            }
                        },
                        ifRight = { pokemonInfo ->
                            _uiState.update { Success(pokemonInfo) }
                        }
                    )
                }
            playCry()
        }
    }

    private fun playCry() {
        viewModelScope.launch(dispatcher) {
            with(_uiState.value) {
                if (this is Success) {
                    playCry(getCryUrl(pokemonInfo.name.replaceFirstChar { it.lowercase() }))
                }
            }
        }
    }

    private suspend fun getCryUrl(pokemonName: String): String =
        withContext(dispatcher) {
            fetchCry(pokemonName)
        }

    fun toggleFavorite() {
        viewModelScope.launch {
            if (_uiState.value is Success) {
                _uiState.update {
                    (it as Success).copy(
                        pokemonInfo = it.pokemonInfo.copy(
                            team = !(_uiState.value as Success).pokemonInfo.team
                        )
                    )
                }
                updatePokemonInfo((_uiState.value as Success).pokemonInfo)
            }
        }
    }
}
