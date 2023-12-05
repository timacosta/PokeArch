package com.architects.pokearch.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface SensorRepositoryContract {
    fun getAccelerometerValue(): Flow<Float>
}
