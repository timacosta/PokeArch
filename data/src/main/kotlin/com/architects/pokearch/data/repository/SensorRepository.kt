package com.architects.pokearch.data.repository

import com.architects.pokearch.data.datasource.SensorSource
import com.architects.pokearch.domain.repository.SensorRepositoryContract
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class SensorRepository @Inject constructor(
    private val accelerometerSensor: SensorSource,
) : SensorRepositoryContract {

    init {
        accelerometerSensor.startListening()
    }

    override fun getAccelerometerValue() =
        callbackFlow {
            accelerometerSensor.setOnSensorValuesChangedListener { values ->
                trySend(values[0])
            }
            awaitClose()
        }
}
