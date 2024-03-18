package com.architects.pokearch.usecases

import arrow.core.Either
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class GetRandomPokemon @Inject constructor(
    private val pokeArchRepositoryContract: PokeArchRepositoryContract,
) {
    operator fun invoke(): Flow<Either<Failure, PokemonInfo>> =
        pokeArchRepositoryContract.randomPokemon()
}
