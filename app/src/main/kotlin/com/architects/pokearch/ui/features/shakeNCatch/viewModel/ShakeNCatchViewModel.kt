package com.architects.pokearch.ui.features.shakeNCatch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.di.annotations.IO
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.ui.features.shakeNCatch.state.ShakeNCatchUiState
import com.architects.pokearch.ui.mapper.DialogData
import com.architects.pokearch.ui.mapper.ErrorDialogManager
import com.architects.pokearch.usecases.GetAccelerometerValue
import com.architects.pokearch.usecases.GetRandomPokemon
import com.architects.pokearch.usecases.Vibrate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShakeNCatchViewModel @Inject constructor(
    private val getAccelerometerValue: GetAccelerometerValue,
    private val getRandomPokemon: GetRandomPokemon,
    private val vibrate: Vibrate,
    @IO private val dispatcher: CoroutineDispatcher,
    private val errorDialogManager: ErrorDialogManager,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ShakeNCatchUiState> =
        MutableStateFlow(ShakeNCatchUiState())
    val uiState = _uiState.asStateFlow()

    private val _dialogState: MutableStateFlow<DialogData?> = MutableStateFlow(null)
    val dialogState = _dialogState.asStateFlow()

    companion object {
        private const val accelerationThreshold = 8
    }

    private var accelerationMin = 0f
    private var accelerationMax = 0f

    init {
        collectAccelerometerValue()
        randomPokemon()
    }

    private fun collectAccelerometerValue() {
        viewModelScope.launch(dispatcher) {
            getAccelerometerValue().collectLatest { acceleration ->
                if (uiState.value.onDetail) return@collectLatest
                _uiState.update { it.copy(acceleration = acceleration) }
                calculateOpenPokeball(acceleration)
            }
        }
    }

    private fun calculateOpenPokeball(value: Float) {
        if (value < accelerationMin) accelerationMin = value
        if (value > accelerationMax) accelerationMax = value
        _uiState.update {
            it.copy(
                openedPokeball = accelerationMin < -accelerationThreshold &&
                        accelerationMax > accelerationThreshold
            )
        }
    }

    private fun randomPokemon() {
        viewModelScope.launch(dispatcher) {
            uiState.map { it.openedPokeball }.distinctUntilChanged().collect { openPokeball ->
                if (openPokeball) {
                    vibrate()
                    getRandomPokemon().collectLatest { result ->
                        result.fold(
                            ifLeft = {failure ->
                                _uiState.update {
                                    it.copy(isLoading = false, error = true)
                                }
                                if (failure is Failure.NetworkError) _dialogState.value = errorDialogManager.transform(
                                    errorType = failure.errorType,
                                    onDismiss = { _dialogState.update { null } }
                                )
                            },
                            ifRight = { pokemonInfo ->
                                _uiState.update {
                                    it.copy(isLoading = false, pokemonInfo = pokemonInfo)
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    fun afterNavigation() {
        viewModelScope.launch(dispatcher) {
            _uiState.value = ShakeNCatchUiState(onDetail = true)
            accelerationMax = 0f
            accelerationMin = 0f
        }
    }

    fun backFromDetail() {
        _uiState.update { it.copy(onDetail = false) }
    }
}
