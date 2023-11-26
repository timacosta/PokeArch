package com.architects.pokearch.ui.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.domain.MeasurableSensor
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeatureViewModel @Inject constructor(
    accelerometerSensor: MeasurableSensor,
    pokeArchRepository: PokeArchRepositoryContract,
) : ViewModel() {

    companion object {
        private const val accelerationThreshold = 15
    }

    private var accelerationMin = 0f
    private var accelerationMax = 0f

    private val _uiState: MutableStateFlow<FeatureUiState> = MutableStateFlow(FeatureUiState())
    val uiState = _uiState.asStateFlow()

    init {
        configAccelerometerSensor(accelerometerSensor)
        randomPokemon(pokeArchRepository)
    }

    private fun configAccelerometerSensor(accelerometerSensor: MeasurableSensor) {
        accelerometerSensor.startListening()
        accelerometerSensor.setOnSensorValuesChangedListener { values ->
            calculateOpenPokeball(values)
        }
    }

    private fun calculateOpenPokeball(values: List<Float>) {
        if (values[0] < accelerationMin) accelerationMin = values[0]
        if (values[0] > accelerationMax) accelerationMax = values[0]
        _uiState.value =
            _uiState.value.copy(
                openedPokeball = accelerationMin < -accelerationThreshold &&
                        accelerationMax > accelerationThreshold
            )
    }

    private fun randomPokemon(pokeArchRepository: PokeArchRepositoryContract) {
        viewModelScope.launch {
            uiState.map { it.openedPokeball }.distinctUntilChanged().collect { openPokeball ->
                if (openPokeball) {
                    pokeArchRepository.randomPokemon().collectLatest { result ->
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
