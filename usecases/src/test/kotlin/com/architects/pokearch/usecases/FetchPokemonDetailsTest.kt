package com.architects.pokearch.usecases

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.domain.model.error.ErrorType
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.samples.pokemonInfoBuilder
import com.architects.pokearch.testing.verifications.coVerifyOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.Rule
import org.junit.Test

class FetchPokemonDetailsTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN FetchPokemonDetails WHEN invokes success THEN calls repository fetchPokemonInfo and returns expected PokemonInfo`() = runTest {
        val expectedResult: Flow<Either<Failure, PokemonInfo>> = flowOf(
            pokemonInfoBuilder().right(),
        )
        val repository: PokeArchRepositoryContract = mockk {
            coEvery { fetchPokemonInfo(any()) } returns expectedResult
        }
        val useCase = FetchPokemonDetails(repository)

        val result = useCase(1)

        coVerifyOnce { repository.fetchPokemonInfo(any()) }
        result shouldBe expectedResult
    }

    @Test
    fun `GIVEN FetchPokemonDetails WHEN invokes fails THEN calls repository fetchPokemonInfo and returns expected Failure`() = runTest {
        val expectedResult: Flow<Either<Failure, PokemonInfo>> =
            flowOf(Failure.NetworkError(ErrorType.NoInternet).left())
        val repository: PokeArchRepositoryContract = mockk {
            coEvery { fetchPokemonInfo(any()) } returns expectedResult
        }
        val useCase = FetchPokemonDetails(repository)

        val result = useCase(1)

        coVerifyOnce { repository.fetchPokemonInfo(any()) }
        result shouldBe expectedResult
    }
}
