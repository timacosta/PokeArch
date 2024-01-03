package com.architects.pokearch.usecases

import arrow.core.Either
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchCry @Inject constructor(
    private val pokeArchRepositoryContract: PokeArchRepositoryContract,
) {
    suspend operator fun invoke(name: String): String = pokeArchRepositoryContract.fetchCry(name)
}