package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.samples.pokemonInfoListBuilder
import com.architects.pokearch.testing.verifications.coVerifyOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.Rule
import org.junit.Test

class GetPokemonTeamTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN GetPokemonTeam WHEN invokes THEN calls repository getPokemonTeam and returns expected result`() = runTest {
        val expectedResult = flowOf(pokemonInfoListBuilder())
        val repository: PokeArchRepositoryContract = mockk {
            coEvery { getPokemonTeam() } returns expectedResult
        }
        val useCase = GetPokemonTeam(repository)

        val result = useCase()

        coVerifyOnce { repository.getPokemonTeam() }
        result shouldBe expectedResult
    }
}