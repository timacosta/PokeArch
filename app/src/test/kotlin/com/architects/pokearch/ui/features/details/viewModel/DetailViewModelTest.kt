package com.architects.pokearch.ui.features.details.viewModel

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.architects.pokearch.R
import com.architects.pokearch.domain.model.error.ErrorType
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.samples.pokemonInfoBuilder
import com.architects.pokearch.testing.verifications.coVerifyOnce
import com.architects.pokearch.ui.features.details.state.DetailUiState
import com.architects.pokearch.ui.mapper.ErrorDialogManager
import com.architects.pokearch.usecases.FetchCry
import com.architects.pokearch.usecases.FetchPokemonDetails
import com.architects.pokearch.usecases.PlayCry
import com.architects.pokearch.usecases.UpdatePokemonInfo
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val fetchPokemonDetails: FetchPokemonDetails = mockk()
    private val fetchCry: FetchCry = mockk()
    private val playCry: PlayCry = mockk()
    private val updatePokemonInfo: UpdatePokemonInfo = mockk()
    private val errorDialogManager: ErrorDialogManager = ErrorDialogManager()

    private lateinit var viewModel: DetailViewModel

    @Before
    fun setUp() {
        val fetchCryStringResult = "pokemon1"

        coEvery { fetchPokemonDetails(pokemonInfoBuilder().id) } returns flowOf(pokemonInfoBuilder().right())
        coEvery { fetchCry(pokemonInfoBuilder().name) } returns fetchCryStringResult
        coJustRun{ playCry(pokemonInfoBuilder().name) }
        coJustRun { updatePokemonInfo(any()) }
    }

    @Test
    fun `GIVEN pokemonInfo WHEN getPokemonDetails THEN state is Success`(): Unit = runTest {
        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo DetailUiState.Loading
            viewModel.getPokemonDetails()
            awaitItem() shouldBeEqualTo DetailUiState.Success(pokemonInfoBuilder())
            cancel()
        }
        coVerifyOrder {
            fetchPokemonDetails(pokemonInfoBuilder().id)
            fetchCry(pokemonInfoBuilder().name)
            playCry(pokemonInfoBuilder().name)
        }
        viewModel.dialogState.value shouldBeEqualTo null
    }


    @Test
    fun `GIVEN network error WHEN getPokemonDetails THEN state is Error`(): Unit = runTest {
        val expectedErrorTitle = R.string.error_title_no_internet
        val expectedErrorMessage = R.string.error_message_no_internet

        coEvery {
            fetchPokemonDetails(pokemonInfoBuilder().id)
        } returns flowOf(Failure.NetworkError(ErrorType.NoInternet).left())

        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo DetailUiState.Loading
            viewModel.getPokemonDetails()
            awaitItem() shouldBeEqualTo DetailUiState.Error
            cancel()
        }
        coVerifyOnce { fetchPokemonDetails(pokemonInfoBuilder().id) }
        viewModel.dialogState.value?.title shouldBeEqualTo expectedErrorTitle
        viewModel.dialogState.value?.message shouldBeEqualTo expectedErrorMessage
    }

    @Test
    fun `GIVEN a pokemon name WHEN getPokemonDetails THEN playCry`(): Unit = runTest {
        viewModel = buildViewModel()
        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo DetailUiState.Loading
            viewModel.getPokemonDetails()
            awaitItem() shouldBeEqualTo DetailUiState.Success(pokemonInfoBuilder())
            cancel()
        }
        coVerifyOnce { playCry(pokemonInfoBuilder().name) }
    }

    @Test
    fun `GIVEN a bad pokemon name WHEN getPokemonDetails THEN playCry return an exception`(): Unit =
        runTest {
            coEvery { playCry("badPokemonName") } throws Exception()
            viewModel = buildViewModel()
            viewModel.uiState.test {
                awaitItem() shouldBeEqualTo DetailUiState.Loading
                viewModel.getPokemonDetails()
                awaitItem() shouldBeEqualTo DetailUiState.Success(pokemonInfoBuilder())
                cancel()
            }
            coVerifyOnce { playCry(any()) }
        }

    @Test
    fun `GIVEN PokemonInfo WHEN updatePokemonInfo THEN team is updated`(): Unit = runTest {
        viewModel = buildViewModel()
        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo DetailUiState.Loading
            viewModel.getPokemonDetails()
            awaitItem() shouldBeEqualTo DetailUiState.Success(pokemonInfoBuilder())
            viewModel.toggleFavorite()
            awaitItem() shouldBeEqualTo DetailUiState.Success(
                pokemonInfoBuilder(team = true)
            )
            cancel()
        }
        coVerifyOnce { updatePokemonInfo((any())) }
    }

    @Test
    fun `GIVEN PokemonInfo WHEN updatePokemonInfo twice THEN team is updated twice`(): Unit =
        runTest {
            viewModel = buildViewModel()
            viewModel.uiState.test {
                awaitItem() shouldBeEqualTo DetailUiState.Loading
                viewModel.getPokemonDetails()
                awaitItem() shouldBeEqualTo DetailUiState.Success(pokemonInfoBuilder())
                viewModel.toggleFavorite()
                awaitItem() shouldBeEqualTo DetailUiState.Success(
                    pokemonInfoBuilder(team = true)
                )
                viewModel.toggleFavorite()
                awaitItem() shouldBeEqualTo DetailUiState.Success(
                    pokemonInfoBuilder(team = false)
                )
                cancel()
            }
            coVerify { updatePokemonInfo((any())) }
        }


    private fun buildViewModel() = DetailViewModel(
        fetchPokemonDetails = fetchPokemonDetails,
        fetchCry = fetchCry,
        playCry = playCry,
        updatePokemonInfo = updatePokemonInfo,
        errorDialogManager = errorDialogManager,
        dispatcher = mainDispatcherRule.testDispatcher,
        pokemonId = 1
    )
}
