package com.architects.pokearch.ui.features.home.viewModel

import app.cash.turbine.test
import com.architects.pokearch.testing.LazyPagingItemsTest
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.samples.pokemonListBuilder
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

    private val expectedPokemonList = pokemonListBuilder()

    @Before
    fun setUp() {
        coEvery { fetchPokemonList() } returns null
        coEvery { getPokemonList(any(), any(), any()) } returns expectedPokemonList
    }

    @Test
    fun `GIVEN fetch pokemon list WHEN init THEN return no error if success`() = runTest {

        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo HomeUiState.Loading
            val state = LazyPagingItemsTest(
                (awaitItem() as HomeUiState.Success).pokemonList,
                mainDispatcherRule.testDispatcher
            )
            state.initPagingDiffer()
            val items = state.itemSnapshotList.items
            items shouldBeEqualTo expectedPokemonList
        }
    }

    private fun buildViewModel() = HomeViewModel(
        getPokemonList = getPokemonList,
        fetchPokemonList = fetchPokemonList,
        dispatcher = mainDispatcherRule.testDispatcher,
        errorDialogManager = errorDialogManager
    )
}