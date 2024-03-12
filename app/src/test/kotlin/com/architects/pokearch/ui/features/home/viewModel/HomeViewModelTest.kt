package com.architects.pokearch.ui.features.home.viewModel

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.testing.paging.LazyPagingItemsTest
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.samples.pokemonListBuilder
import com.architects.pokearch.testing.verifications.coVerifyOnce
import com.architects.pokearch.ui.components.pagingsource.PokemonPagingFlowBuilder
import com.architects.pokearch.ui.features.home.state.HomeUiState
import com.architects.pokearch.ui.mapper.ErrorDialogManager
import com.architects.pokearch.usecases.FetchPokemonList
import com.architects.pokearch.usecases.GetPokemonList
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getPokemonList: GetPokemonList = mockk()
    private val fetchPokemonList: FetchPokemonList = mockk()
    private val errorDialogManager: ErrorDialogManager = ErrorDialogManager()
    private val pokemonPagingFlowBuilder: PokemonPagingFlowBuilder = mockk()

    private lateinit var viewModel: HomeViewModel

    private val expectedPokemonList = pokemonListBuilder()

    @Before
    fun setUp() {
        coEvery { fetchPokemonList() } returns null
        coEvery { getPokemonList(any(), any(), any()) } returns expectedPokemonList
        coEvery {
            pokemonPagingFlowBuilder(any(), any(), any())
        } returns pokemonPagingFlowBuilderFake(getPokemonList)
    }

    @Test
    fun `GIVEN fetch pokemon list WHEN init THEN return no error if success`() = runTest {

        viewModel = buildViewModel()

        viewModel.uiState.test {
            val state = LazyPagingItemsTest(
                (awaitItem() as HomeUiState.Success).pokemonList,
                mainDispatcherRule.testDispatcher
            )
            state.initPagingDiffer()
            val items = state.itemSnapshotList.items
            items shouldBeEqualTo expectedPokemonList
        }

        coVerifyOnce { fetchPokemonList() }
        coVerifyOnce { getPokemonList(any(), any(), any()) }
        coVerifyOnce { pokemonPagingFlowBuilder(any(), any(), any()) }
    }

    private fun buildViewModel() = HomeViewModel(
        getPokemonList = getPokemonList,
        fetchPokemonList = fetchPokemonList,
        dispatcher = mainDispatcherRule.testDispatcher,
        errorDialogManager = errorDialogManager,
        pokemonPagingFlowBuilder = pokemonPagingFlowBuilder,
    )

    private fun pokemonPagingFlowBuilderFake(
        getPokemonList: GetPokemonList,
        pokemonName: String = "",
        loadState: LoadState = LoadState.NotLoading(true),
    ): Flow<PagingData<Pokemon>> {
        val pokemonList = runBlocking { getPokemonList(pokemonName) }
        val loadStates = LoadStates(loadState, loadState, loadState)
        val pagingData = PagingData.from(pokemonList, loadStates)
        return flowOf(pagingData)
    }
}
