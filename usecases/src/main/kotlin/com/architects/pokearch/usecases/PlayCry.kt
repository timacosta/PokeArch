package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.MediaPlayerRepositoryContract
import javax.inject.Inject

class PlayCry @Inject constructor(
    private val pokeArchRepositoryContract: MediaPlayerRepositoryContract,
) {
    suspend operator fun invoke(url: String) = pokeArchRepositoryContract.playCry(url)
}
