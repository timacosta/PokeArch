package com.architects.pokearch.core.framework.network

import android.util.Log
import arrow.core.Either
import com.architects.pokearch.core.framework.network.interceptor.InternetConnectivityException
import com.architects.pokearch.core.framework.network.model.NetworkPokemon
import com.architects.pokearch.core.framework.network.model.NetworkPokemonInfo
import com.architects.pokearch.core.framework.network.model.NetworkPokemons
import com.architects.pokearch.core.framework.network.service.CryService
import com.architects.pokearch.core.framework.network.service.PokedexService
import com.architects.pokearch.domain.model.error.ErrorType
import com.architects.pokearch.domain.model.error.Failure
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class PokemonServerDataSourceTest {

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.e(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
    }

    @Test
    fun `GIVEN Success WHEN getPokemonList THEN return expected result`() = runTest {
        val pokedexServiceMockk: PokedexService = mockk {
            coEvery { fetchPokemonList(any(), any()) } returns Response.success(
                NetworkPokemons(
                    count = 20,
                    next = null,
                    previous = null,
                    results = listOf(
                        NetworkPokemon(
                            name = "Pikachu",
                            url = "/pokemon/pikachu"
                        )
                    )
                )
            )
        }

        val dataSource = buildDataSource(
            pokedexService = pokedexServiceMockk,
            cryService = mockk()
        )

        dataSource.getPokemonList().let { result ->
            (result as Either.Right).value.first().name shouldBeEqualTo "Pikachu"
        }
    }

    @Test
    fun `GIVEN Failure WHEN getPokemonList THEN return expected result`() = runTest {
        val pokedexServiceMockk: PokedexService = mockk {
            coEvery { fetchPokemonList(any(), any()) } returns Response.error(
                400, "error".toResponseBody(null)
            )
        }

        val dataSource = buildDataSource(
            pokedexService = pokedexServiceMockk,
            cryService = mockk()
        )

        dataSource.getPokemonList().let { result ->
            (result as Either.Left).value shouldBeEqualTo Failure.NetworkError(ErrorType.BadRequest)
        }
    }

    @Test
    fun `GIVEN Exception WHEN getPokemonList THEN return unknown failure`() = runTest {
        val pokedexServiceMockk: PokedexService = mockk {
            coEvery { fetchPokemonList(any(), any()) } throws Exception()
        }

        val dataSource = buildDataSource(
            pokedexService = pokedexServiceMockk,
            cryService = mockk()
        )

        dataSource.getPokemonList().let { result ->
            (result as Either.Left).value shouldBeEqualTo Failure.UnknownError
        }
    }

    @Test
    fun `GIVEN InternetConnectivityException WHEN getPokemonList THEN return unknown failure`() =
        runTest {
            val pokedexServiceMockk: PokedexService = mockk {
                coEvery { fetchPokemonList(any(), any()) } throws InternetConnectivityException()
            }

            val dataSource = buildDataSource(
                pokedexService = pokedexServiceMockk,
                cryService = mockk()
            )

            dataSource.getPokemonList().let { result ->
                (result as Either.Left).value shouldBeEqualTo Failure.NetworkError(ErrorType.NoInternet)
            }
        }

    @Test
    fun `GIVEN Success WHEN areMorePokemonAvailableFrom THEN return expected result`() = runTest {
        val pokedexServiceMockk: PokedexService = mockk {
            coEvery { fetchPokemonList(any(), any()) } returns Response.success(
                NetworkPokemons(
                    count = 20,
                    next = "next",
                    previous = null,
                    results = emptyList()
                )
            )
        }

        val dataSource = buildDataSource(
            pokedexService = pokedexServiceMockk,
            cryService = mockk()
        )

        dataSource.areMorePokemonAvailableFrom(20).let { result ->
            (result as Either.Right).value shouldBeEqualTo true
        }
    }

    @Test
    fun `GIVEN Next is null WHEN areMorePokemonAvailableFrom THEN return expected result`() =
        runTest {
            val pokedexServiceMockk: PokedexService = mockk {
                coEvery { fetchPokemonList(any(), any()) } returns Response.success(
                    NetworkPokemons(
                        count = 20,
                        next = null,
                        previous = null,
                        results = emptyList()
                    )
                )
            }

            val dataSource = buildDataSource(
                pokedexService = pokedexServiceMockk,
                cryService = mockk()
            )

            dataSource.areMorePokemonAvailableFrom(20) shouldBeEqualTo Either.Right(false)
        }


    @Test
    fun `GIVEN Failure WHEN areMorePokemonAvailableFrom THEN return expected result`() = runTest {
        val pokedexServiceMockk: PokedexService = mockk {
            coEvery { fetchPokemonList(any(), any()) } returns Response.error(
                400, "error".toResponseBody(null)
            )
        }

        val dataSource = buildDataSource(
            pokedexService = pokedexServiceMockk,
            cryService = mockk()
        )

        dataSource.areMorePokemonAvailableFrom(20).let { result ->
            (result as Either.Left).value shouldBeEqualTo Failure.NetworkError(ErrorType.BadRequest)
        }
    }

    @Test
    fun `GIVEN Exception WHEN areMorePokemonAvailableFrom THEN return unknown failure`() = runTest {
        val pokedexServiceMockk: PokedexService = mockk {
            coEvery { fetchPokemonList(any(), any()) } throws Exception()
        }

        val dataSource = buildDataSource(
            pokedexService = pokedexServiceMockk,
            cryService = mockk()
        )

        dataSource.areMorePokemonAvailableFrom(20).let { result ->
            (result as Either.Left).value shouldBeEqualTo Failure.UnknownError
        }
    }

    @Test
    fun `GIVEN InternetConnectivityException WHEN areMorePokemonAvailableFrom THEN return unknown failure`() =
        runTest {
            val pokedexServiceMockk: PokedexService = mockk {
                coEvery { fetchPokemonList(any(), any()) } throws InternetConnectivityException()
            }

            val dataSource = buildDataSource(
                pokedexService = pokedexServiceMockk,
                cryService = mockk()
            )

            dataSource.areMorePokemonAvailableFrom(20).let { result ->
                (result as Either.Left).value shouldBeEqualTo Failure.NetworkError(ErrorType.NoInternet)
            }
        }

    @Test
    fun `GIVEN Success WHEN getPokemon THEN return expected result`() = runTest {
        val pokedexServiceMockk: PokedexService = mockk {
            coEvery { fetchPokemonInfo(any()) } returns Response.success(
                NetworkPokemonInfo(
                    id = 0,
                    name = "Pikachu",
                    height = 0,
                    weight = 0,
                    experience = 0,
                    types = listOf(),
                    stats = listOf(),
                    team = false
                )
            )
        }

        val dataSource = buildDataSource(
            pokedexService = pokedexServiceMockk,
            cryService = mockk()
        )

        dataSource.getPokemon(1).let { result ->
            (result as Either.Right).value.name shouldBeEqualTo "Pikachu"
        }
    }

    @Test
    fun `GIVEN Failure WHEN getPokemon THEN return expected result`() = runTest {
        val pokedexServiceMockk: PokedexService = mockk {
            coEvery { fetchPokemonInfo(any()) } returns Response.error(
                400, "error".toResponseBody(null)
            )
        }

        val dataSource = buildDataSource(
            pokedexService = pokedexServiceMockk,
            cryService = mockk()
        )

        dataSource.getPokemon(1).let { result ->
            (result as Either.Left).value shouldBeEqualTo Failure.NetworkError(ErrorType.BadRequest)
        }
    }

    @Test
    fun `GIVEN Exception WHEN getPokemon THEN return unknown failure`() = runTest {
        val pokedexServiceMockk: PokedexService = mockk {
            coEvery { fetchPokemonInfo(any()) } throws Exception()
        }

        val dataSource = buildDataSource(
            pokedexService = pokedexServiceMockk,
            cryService = mockk()
        )

        dataSource.getPokemon(1).let { result ->
            (result as Either.Left).value shouldBeEqualTo Failure.UnknownError
        }
    }

    @Test
    fun `GIVEN InternetConnectivityException WHEN getPokemon THEN return unknown failure`() =
        runTest {
            val pokedexServiceMockk: PokedexService = mockk {
                coEvery { fetchPokemonInfo(any()) } throws InternetConnectivityException()
            }

            val dataSource = buildDataSource(
                pokedexService = pokedexServiceMockk,
                cryService = mockk()
            )

            dataSource.getPokemon(1).let { result ->
                (result as Either.Left).value shouldBeEqualTo Failure.NetworkError(ErrorType.NoInternet)
            }
        }

    @Test
    fun `GIVEN Success WHEN tryCatchCry THEN return expected result`() = runTest {
        val cryServiceMockk: CryService = mockk {
            coEvery { thereIsCry(any()) } returns Response.success("cry".toResponseBody())
        }

        val dataSource = buildDataSource(
            pokedexService = mockk(),
            cryService = cryServiceMockk
        )

        dataSource.tryCatchCry("pikachu") {
            it shouldBeEqualTo Either.Right("https://play.pokemonshowdown.com/audio/cries/pikachu.mp3")
        }
    }

    @Test
    fun `GIVEN Failure WHEN tryCatchCry THEN return expected result`() = runTest {
        val cryServiceMockk: CryService = mockk {
            coEvery { thereIsCry(any()) } returns Response.error(
                400, "error".toResponseBody(null)
            )
        }

        val dataSource = buildDataSource(
            pokedexService = mockk(),
            cryService = cryServiceMockk
        )

        dataSource.tryCatchCry("pikachu") {
            it shouldBeEqualTo Either.Left(Failure.NetworkError(ErrorType.BadRequest))
        }
    }

    private fun buildDataSource(pokedexService: PokedexService, cryService: CryService) =
        PokemonServerDataSource(
            pokedexService = pokedexService,
            cryService = cryService
        )
}