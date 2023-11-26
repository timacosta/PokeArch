package com.architects.pokearch.ui.feature

import androidx.lifecycle.ViewModel
import com.architects.pokearch.core.domain.MeasurableSensor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class FeatureViewModel @Inject constructor(
    accelerometerSensor: MeasurableSensor
) : ViewModel() {

    companion object{
        private const val accelerationThreshold = 15
    }

    private var accelerationMin = 0f
    private var accelerationMax = 0f
    private val _openPokeball: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val openPokeball = _openPokeball.asStateFlow()

    init {
        accelerometerSensor.startListening()
        accelerometerSensor.setOnSensorValuesChangedListener {  values ->
            if (values[0] < accelerationMin) accelerationMin = values[0]
            if (values[0] > accelerationMax) accelerationMax = values[0]
            _openPokeball.value = accelerationMin < -accelerationThreshold && accelerationMax > accelerationThreshold
        }
    }
}
