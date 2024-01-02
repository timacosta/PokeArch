package com.architects.pokearch.usecases

import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import javax.inject.Inject

class FetchPokemonList @Inject constructor(
    private val pokeArchRepositoryContract: PokeArchRepositoryContract,
) {
    suspend operator fun invoke(): Failure? = pokeArchRepositoryContract.fetchPokemonList()
}