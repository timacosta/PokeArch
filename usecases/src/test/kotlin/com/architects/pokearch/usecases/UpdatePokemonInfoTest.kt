package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.samples.pokemonInfoBuilder
import com.architects.pokearch.testing.verifications.coVerifyOnce
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class UpdatePokemonInfoTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN UpdatePokemonInfo WHEN invokes THEN calls repository updatePokemonInfo`() = runTest {
        val expectedPokemonInfo = pokemonInfoBuilder()
        val repository: PokeArchRepositoryContract = mockk {
            coJustRun { updatePokemonInfo(any()) }
        }
        val useCase = UpdatePokemonInfo(repository)

        useCase(expectedPokemonInfo)

        coVerifyOnce { repository.updatePokemonInfo(expectedPokemonInfo) }
    }
}