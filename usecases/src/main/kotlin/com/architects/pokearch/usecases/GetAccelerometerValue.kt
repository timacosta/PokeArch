package com.architects.pokearch.usecases

import arrow.core.Either
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.domain.repository.SensorRepositoryContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAccelerometerValue @Inject constructor(
    private val sensorRepositoryContract: SensorRepositoryContract,
) {
    operator fun invoke(): Flow<Float> = sensorRepositoryContract.getAccelerometerValue()
}