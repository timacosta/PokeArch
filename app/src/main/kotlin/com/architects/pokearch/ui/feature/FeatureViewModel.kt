package com.architects.pokearch.ui.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.core.domain.repository.SensorRepositoryContract
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
    sensorRepository: SensorRepositoryContract,
    pokeArchRepository: PokeArchRepositoryContract,
) : ViewModel() {

    private val _uiState: MutableStateFlow<FeatureUiState> = MutableStateFlow(FeatureUiState())
    val uiState = _uiState.asStateFlow()

    companion object {
        private const val accelerationThreshold = 8
    }

    private var accelerationMin = 0f
    private var accelerationMax = 0f

    init {
        getAccelerometerValue(sensorRepository)
        randomPokemon(pokeArchRepository)
    }

    private fun getAccelerometerValue(sensorRepository: SensorRepositoryContract) {
        viewModelScope.launch {
            sensorRepository.getAccelerometerValue().collectLatest {
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
