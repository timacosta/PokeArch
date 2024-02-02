package com.architects.pokearch.data.repository

import app.cash.turbine.test
import arrow.core.Either
import com.architects.pokearch.data.datasource.PokemonLocalDataSource
import com.architects.pokearch.data.datasource.PokemonRemoteDataSource
import com.architects.pokearch.data.rules.MainDispatcherRule
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.PokemonInfo
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldBeNull
import org.junit.Rule
import org.junit.Test

class PokeArchRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN default values WHEN getPokemonList THEN return expected result`() = runTest {
        val filter = ""
        val page = 0
        val limit = 20
        val offset = page * limit
        val pokemonList = listOf(
            Pokemon(
                name = "Pikachu",
                url = "/pokemon/pikachu"
            )
        )
        val repository: PokeArchRepository = buildPokeArchRepository(
            localDataSource = mockk {
                coEvery {
                    getPokemonList(
                        filter,
                        limit,
                        offset
                    )
                } returns pokemonList
            }
        )

        val result = repository.getPokemonList()

        result.first().run {
            name shouldBeEqualTo "Pikachu"
            url shouldBeEqualTo "/pokemon/pikachu"
        }
    }

    @Test
    fun `GIVEN areMorePokemonAvailableFrom returns false WHEN fetchPokemonList THEN no network call performed`() = runTest {
        val count = 20
        val pokemonList = listOf(
            Pokemon(
                name = "Pikachu",
                url = "/pokemon/pikachu"
            )
        )
        val remoteDataSource: PokemonRemoteDataSource = mockk {
            coEvery { areMorePokemonAvailableFrom(count) } returns Either.Right(false)
            coEvery { getPokemonList() } returns Either.Right(pokemonList)
        }
        val localDataSource: PokemonLocalDataSource = mockk {
            coEvery { countPokemon() } returns count
            coEvery { savePokemonList(pokemonList) } just runs
        }
        val repository = buildPokeArchRepository(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource
        )

        val result = repository.fetchPokemonList()

        coVerify (exactly = 0) { remoteDataSource.getPokemonList(any(), any()) }
        result.shouldBeNull()
    }

  @Test
    fun `GIVEN valid id WHEN fetchPokemonInfo THEN return expected result`() = runTest {
        val id = 1
        val pokemonInfo = PokemonInfo(
            id = 1,
            name = "Pikachu",
            height = 9380,
            weight = 1636,
            experience = 8341,
            types = listOf(),
            stats = listOf(),
            team = false
        )
        val repository: PokeArchRepository = buildPokeArchRepository(
            localDataSource = mockk {
                coEvery { getPokemonInfo(id) } returns pokemonInfo
            }
        )

        repository.fetchPokemonInfo(id).test {
            awaitItem().onRight {
                it.name shouldBeEqualTo "Pikachu"
                it.experience shouldBeEqualTo 8341
            }
        }
    }

    /*@Test
    fun `GIVEN invalid id WHEN fetchPokemonInfo THEN return Failure`() = runTest {
        val id = -1

        val repository: PokeArchRepository = buildPokeArchRepository(
            localDataSource = mockk {
                coEvery { getPokemonInfo(id) } returns null
            },
            remoteDataSource = mockk {
                coEvery { getPokemon(id) } returns Either.Left(Failure.NetworkConnection)
            }
        )

        val result = repository.fetchPokemonInfo(id)

        result.first().run {
            this shouldBeEqualTo Either.Left(Failure.NetworkConnection)
        }
    }*/

    @Test
    fun `GIVEN valid name WHEN fetchCry THEN return expected result`() = runTest {
        val name = "pikachu"
        val cryUrl = "https://play.pokemonshowdown.com/audio/cries/pikachu.mp3"

        val repository: PokeArchRepository = buildPokeArchRepository(
            remoteDataSource = mockk {
                coEvery { tryCatchCry(name, any()) } answers { secondArg<(String) -> Unit>()(name) }
            }
        )

        val result = repository.fetchCry(name)

        result shouldBeEqualTo cryUrl
    }

    @Test
    fun `GIVEN invalid name WHEN fetchCry THEN return default result`() = runTest {
        val name = "invalid"
        val defaultCryUrl = "https://play.pokemonshowdown.com/audio/cries/.mp3"

        val repository: PokeArchRepository = buildPokeArchRepository(
            remoteDataSource = mockk {
                coEvery { tryCatchCry(name, any()) } answers { secondArg<(String) -> Unit>()("") }
            }
        )

        val result = repository.fetchCry(name)

        result shouldBeEqualTo defaultCryUrl
    }



    private fun buildPokeArchRepository(
        remoteDataSource: PokemonRemoteDataSource = mockk(),
        localDataSource: PokemonLocalDataSource = mockk(),
    ) = PokeArchRepository(
        remoteDataSource = remoteDataSource,
        localDataSource = localDataSource
    )
}