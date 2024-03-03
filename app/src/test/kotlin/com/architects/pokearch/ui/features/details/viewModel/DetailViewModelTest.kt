package com.architects.pokearch.ui.features.details.viewModel

import app.cash.turbine.test
import arrow.core.right
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
        coEvery { fetchPokemonDetails(1) } returns flowOf(pokemonInfoBuilder().right())
        coEvery { fetchCry(pokemonInfoBuilder().name) } returns "pokemon1"
        coEvery { playCry(any()) } returns Unit
    }

    @Test
    fun `GIVEN pokemonId WHEN init THEN states is Success`() = runTest {
        viewModel = buildViewModel()

        viewModel.pokemonDetailInfo.test {
            awaitItem() shouldBeEqualTo DetailUiState.Success(pokemonInfoBuilder())
        }
        coVerifyOnce { fetchPokemonDetails(1) }
        coVerifyOnce { fetchCry(pokemonInfoBuilder().name) }
        coVerifyOnce { playCry(pokemonInfoBuilder().name) }
        viewModel.dialogState.value shouldBeEqualTo null
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
