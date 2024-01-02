package com.architects.pokearch.ui.features.shakeNCatch.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.ui.features.shakeNCatch.state.ShakeNCatchUiState
import com.architects.pokearch.usecases.GetAccelerometerValue
import com.architects.pokearch.usecases.GetRandomPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShakeNCatchViewModel @Inject constructor(
    private val getAccelerometerValue: GetAccelerometerValue,
    private val getRandomPokemon: GetRandomPokemon,
) : ViewModel() {

    private val _uiState: MutableStateFlow<ShakeNCatchUiState> = MutableStateFlow(ShakeNCatchUiState())
    val uiState = _uiState.asStateFlow()

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
        viewModelScope.launch {
            getAccelerometerValue().collectLatest {
                calculateOpenPokeball(it)
            }
        }
    }

    private fun calculateOpenPokeball(value: Float) {
        if (value < accelerationMin) accelerationMin = value
        if (value > accelerationMax) accelerationMax = value
        _uiState.value =
            _uiState.value.copy(
                openedPokeball = accelerationMin < -accelerationThreshold &&
                        accelerationMax > accelerationThreshold
            )
    }

    private fun randomPokemon() {
        viewModelScope.launch {
            uiState.map { it.openedPokeball }.distinctUntilChanged().collect { openPokeball ->
                if (openPokeball) {
                    getRandomPokemon().collectLatest { result ->
                        result.fold(
                            ifLeft = {
                                _uiState.value =
                                    _uiState.value.copy(isLoading = false, error = true)
                            },
                            ifRight = {
                                _uiState.value =
                                    _uiState.value.copy(isLoading = false, pokemonInfo = it)
                            }
                        )
                    }
                }
            }
        }
    }
}
