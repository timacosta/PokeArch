package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import javax.inject.Inject

class GetPokemonTeam @Inject constructor(
    private val pokemonRepository: PokeArchRepositoryContract,
) {
    operator fun invoke() = pokemonRepository.getPokemonTeam()
}
