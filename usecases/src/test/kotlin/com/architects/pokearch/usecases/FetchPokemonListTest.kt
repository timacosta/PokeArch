package com.architects.pokearch.usecases

import com.architects.pokearch.domain.model.error.ErrorType
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.verifications.coVerifyOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.Rule
import org.junit.Test

class FetchPokemonListTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN FetchPokemonList WHEN invokes success THEN calls repository fetchPokemonList and returns null`() = runTest {
        val expectedResult: Failure? = null
        val repository: PokeArchRepositoryContract = mockk {
            coEvery { fetchPokemonList() } returns expectedResult
        }
        val useCase = FetchPokemonList(repository)

        val result = useCase()

        coVerifyOnce { repository.fetchPokemonList() }
        result shouldBe expectedResult
    }

    @Test
    fun `GIVEN FetchPokemonList WHEN invokes fails THEN calls repository fetchPokemonList and returns Failure`() = runTest {
        val expectedResult: Failure = Failure.NetworkError(ErrorType.NoInternet)
        val repository: PokeArchRepositoryContract = mockk {
            coEvery { fetchPokemonList() } returns expectedResult
        }
        val useCase = FetchPokemonList(repository)

        val result = useCase()

        coVerifyOnce { repository.fetchPokemonList() }
        result shouldBe expectedResult
    }
}
