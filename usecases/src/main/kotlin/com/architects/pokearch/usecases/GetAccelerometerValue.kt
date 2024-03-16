package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.SensorRepositoryContract
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetAccelerometerValue @Inject constructor(
    private val sensorRepositoryContract: SensorRepositoryContract
) {
    operator fun invoke(): Flow<Float> = sensorRepositoryContract.getAccelerometerValue()
}
