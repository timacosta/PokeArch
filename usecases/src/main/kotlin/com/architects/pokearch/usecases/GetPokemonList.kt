package com.architects.pokearch.usecases

import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import javax.inject.Inject

class GetPokemonList @Inject constructor(
    private val pokemonRepository: PokeArchRepositoryContract,
) {
    suspend operator fun invoke(filter: String = "", page: Int = 0, limit: Int = 20): List<Pokemon> =
        pokemonRepository.getPokemonList(filter, page, limit)
}
