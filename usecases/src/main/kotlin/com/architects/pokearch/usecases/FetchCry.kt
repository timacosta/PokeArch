package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import javax.inject.Inject

class FetchCry @Inject constructor(
    private val pokeArchRepositoryContract: PokeArchRepositoryContract,
) {
    suspend operator fun invoke(name: String): String = pokeArchRepositoryContract.fetchCry(name)
}
