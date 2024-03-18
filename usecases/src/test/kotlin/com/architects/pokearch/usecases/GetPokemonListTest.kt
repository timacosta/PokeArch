package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.samples.pokemonListBuilder
import com.architects.pokearch.testing.verifications.coVerifyOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.Rule
import org.junit.Test

class GetPokemonListTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN GetPokemonList WHEN invokes THEN calls repository getPokemonList and returns expected result`() = runTest {
        val expectedResult = pokemonListBuilder()
        val repository: PokeArchRepositoryContract = mockk {
            coEvery { getPokemonList() } returns expectedResult
        }
        val useCase = GetPokemonList(repository)

        val result = useCase()

        coVerifyOnce { repository.getPokemonList() }
        result shouldBe expectedResult
    }
}
