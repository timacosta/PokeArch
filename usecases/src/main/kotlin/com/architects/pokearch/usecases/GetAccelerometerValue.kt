package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.SensorRepositoryContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccelerometerValue @Inject constructor(
    private val sensorRepositoryContract: SensorRepositoryContract,
) {
    operator fun invoke(): Flow<Float> = sensorRepositoryContract.getAccelerometerValue()
}