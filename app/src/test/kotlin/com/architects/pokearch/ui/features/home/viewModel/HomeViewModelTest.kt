package com.architects.pokearch.ui.features.home.viewModel

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.testing.paging.LazyPagingItemsTest
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.samples.pokemonBuilder
import com.architects.pokearch.testing.samples.pokemonListBuilder
import com.architects.pokearch.testing.verifications.coVerifyOnce
import com.architects.pokearch.ui.components.pagingsource.PokemonPagingSourceFlowBuilder
import com.architects.pokearch.ui.features.home.state.HomeUiState
import com.architects.pokearch.ui.mapper.ErrorDialogManager
import com.architects.pokearch.usecases.FetchPokemonList
import com.architects.pokearch.usecases.GetPokemonList
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.delay
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
    private val pokemonPagingFlowBuilder: PokemonPagingSourceFlowBuilder = mockk()

    private lateinit var viewModel: HomeViewModel

    private val expectedPokemonList = pokemonListBuilder()

    @Before
    fun setUp() {
        coEvery { fetchPokemonList() } coAnswers {
            delay(100) // Short delay to simulate network latency
            null
        }
        coEvery { getPokemonList() } returns expectedPokemonList
        coEvery {
            pokemonPagingFlowBuilder(
                pokemonName = any(),
                getPokemonList = any(),
                viewModelScope = any()
            )
        } returns pokemonPagingFlowBuilderFake(getPokemonList)
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
            cancel()
        }

        coVerifyOnce { fetchPokemonList() }
        coVerifyOnce { getPokemonList() }
        coVerifyOnce { pokemonPagingFlowBuilder(any(), any(), any()) }
    }

    @Test
    fun `GIVEN a pokemon name WHEN getPokemonList THEN return exact coincidence`() = runTest {

        val expectedPokemonName = pokemonBuilder(id = 5).name
        val expectedCoincidence = expectedPokemonList.filter { pokemon ->
            pokemon.name.contains(expectedPokemonName, ignoreCase = true)
        }

        coEvery { getPokemonList(expectedPokemonName) } returns expectedCoincidence

        coEvery {
            pokemonPagingFlowBuilder(
                pokemonName = expectedPokemonName,
                getPokemonList = getPokemonList,
                viewModelScope = any(),
            )
        } returns pokemonPagingFlowBuilderFake(
            pokemonName = expectedPokemonName,
            getPokemonList = getPokemonList,
        )

        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo HomeUiState.Loading
            val state = LazyPagingItemsTest(
                (awaitItem() as HomeUiState.Success).pokemonList,
                mainDispatcherRule.testDispatcher
            )
            state.initPagingDiffer()
            val items = state.itemSnapshotList.items

            items.size shouldBeEqualTo expectedPokemonList.size

            viewModel.getPokemonListFromDb(expectedPokemonName)

            val newState = LazyPagingItemsTest(
                (awaitItem() as HomeUiState.Success).pokemonList,
                mainDispatcherRule.testDispatcher
            )
            newState.initPagingDiffer()
            val newItems = newState.itemSnapshotList.items

            newItems shouldBeEqualTo expectedCoincidence
            cancel()
        }

        coVerifyOnce { getPokemonList(expectedPokemonName) }
        verify(exactly = 1) {
            pokemonPagingFlowBuilder(
                pokemonName = expectedPokemonName,
                getPokemonList = getPokemonList,
                viewModelScope = any(),
            )
        }
    }

    @Test
    fun `GIVEN a pokemon name WHEN getPokemonList THEN return coincidences`() = runTest {

        val expectedPokemonName = "pikachu"
        val pokemonList: List<Pokemon> = listOf(
            pokemonBuilder().copy(
                name = "$expectedPokemonName 1",
            ),
            pokemonBuilder().copy(
                name = "$expectedPokemonName 2",
            ),
            pokemonBuilder().copy(
                name = "$expectedPokemonName 3",
            ),
            pokemonBuilder(id = 4),
            pokemonBuilder(id = 5),
        )

        val expectedCoincidence = pokemonList.filter { pokemon ->
            pokemon.name.contains(expectedPokemonName, ignoreCase = true)
        }

        coEvery { getPokemonList(expectedPokemonName) } returns expectedCoincidence

        coEvery {
            pokemonPagingFlowBuilder(
                pokemonName = expectedPokemonName,
                getPokemonList = getPokemonList,
                viewModelScope = any(),
            )
        } returns pokemonPagingFlowBuilderFake(
            pokemonName = expectedPokemonName,
            getPokemonList = getPokemonList,
        )

        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo HomeUiState.Loading
            val state = LazyPagingItemsTest(
                (awaitItem() as HomeUiState.Success).pokemonList,
                mainDispatcherRule.testDispatcher
            )
            state.initPagingDiffer()
            val items = state.itemSnapshotList.items

            items.size shouldBeEqualTo expectedPokemonList.size

            viewModel.getPokemonListFromDb(expectedPokemonName)

            val newState = LazyPagingItemsTest(
                (awaitItem() as HomeUiState.Success).pokemonList,
                mainDispatcherRule.testDispatcher
            )
            newState.initPagingDiffer()
            val newItems = newState.itemSnapshotList.items

            newItems.size shouldBeEqualTo 3

            newItems.forEachIndexed { index, pokemon ->
                pokemon.name shouldBeEqualTo "$expectedPokemonName ${index + 1}"
            }

            cancel()
        }

        coVerifyOnce { getPokemonList(expectedPokemonName) }
    }


    @Test
    fun `GIVEN failure WHEN fetchPokemonList THEN return Failure`() = runTest {

        coEvery { fetchPokemonList() } returns Failure.UnknownError

        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo HomeUiState.Error(Failure.UnknownError)
            cancel()
        }

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
