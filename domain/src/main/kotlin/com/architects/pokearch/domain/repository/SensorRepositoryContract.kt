package com.architects.pokearch.domain.repository

import kotlinx.coroutines.flow.Flow

interface SensorRepositoryContract {
    fun getAccelerometerValue(): Flow<Float>
}
