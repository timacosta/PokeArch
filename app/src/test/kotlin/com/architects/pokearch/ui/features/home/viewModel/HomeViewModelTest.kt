package com.architects.pokearch.ui.features.home.viewModel

import app.cash.turbine.test
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.verifications.coVerifyOnce
import com.architects.pokearch.ui.features.home.state.HomeUiState
import com.architects.pokearch.ui.mapper.ErrorDialogManager
import com.architects.pokearch.usecases.FetchPokemonList
import com.architects.pokearch.usecases.GetPokemonList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest{
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getPokemonList: GetPokemonList = mockk()
    private val fetchPokemonList: FetchPokemonList = mockk()
    private val errorDialogManager: ErrorDialogManager = ErrorDialogManager()

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setUp() {
        coEvery { fetchPokemonList() } returns null
    }

    @Test
    fun `GIVEN fetch pokemon list WHEN init THEN return no error if success`() = runTest {
        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo  HomeUiState.Loading
            awaitItem() shouldBeEqualTo  null
            cancel()
        }
        coVerifyOnce { fetchPokemonList() }
    }

    private fun buildViewModel() = HomeViewModel(
        getPokemonList = getPokemonList,
        fetchPokemonList = fetchPokemonList,
        dispatcher = mainDispatcherRule.testDispatcher,
        errorDialogManager = errorDialogManager
    )
}