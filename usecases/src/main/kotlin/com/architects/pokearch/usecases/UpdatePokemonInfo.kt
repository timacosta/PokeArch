package com.architects.pokearch.usecases

import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import javax.inject.Inject

class UpdatePokemonInfo @Inject constructor(
    private val pokeArchRepository: PokeArchRepositoryContract
) {
    suspend operator fun invoke(pokemon: PokemonInfo) =
        pokeArchRepository.updatePokemonInfo(pokemon)
}
