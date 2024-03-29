package com.architects.pokearch.ui.features.home.viewModel

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import app.cash.turbine.test
import com.architects.pokearch.R
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.domain.model.error.ErrorType
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.advanceUntilIdle
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
    fun `GIVEN fetch pokemon list WHEN init THEN return Success`() = runTest {

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
    fun `GIVEN a pokemon name WHEN getPokemonList THEN return Success`() = runTest {

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
            extractPagingItems(
                (awaitItem() as HomeUiState.Success).pokemonList
            ) { items ->
                items.size shouldBeEqualTo 10
            }

            viewModel.getPokemonList(expectedPokemonName)

            extractPagingItems(
                (awaitItem() as HomeUiState.Success).pokemonList
            ) { items ->
                items shouldBeEqualTo expectedCoincidence
            }
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
    fun `GIVEN unknown error WHEN fetchPokemonList THEN return failure screen`() = runTest {

        coEvery { fetchPokemonList() } coAnswers {
            delay(100)
            Failure.UnknownError
        }

        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo HomeUiState.Loading
            awaitItem() shouldBeEqualTo HomeUiState.Error(Failure.UnknownError)
            cancel()
        }

        coVerifyOnce { fetchPokemonList() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN network error WHEN fetchPokemonList THEN return network failure`() = runTest {

        val expectedErrorTitle = R.string.error_title_no_internet
        val expectedErrorMessage = R.string.error_message_no_internet

        coEvery { fetchPokemonList() } coAnswers {
            delay(100)
            Failure.NetworkError(ErrorType.NoInternet)
        }

        viewModel = buildViewModel()

        viewModel.uiState.test {
            awaitItem() shouldBeEqualTo HomeUiState.Loading
            cancel()
        }
        advanceUntilIdle()

        coVerifyOnce { fetchPokemonList() }
        viewModel.dialogState.value?.title shouldBeEqualTo expectedErrorTitle
        viewModel.dialogState.value?.message shouldBeEqualTo expectedErrorMessage
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `GIVEN network error WHEN onDialogDismiss THEN getPokemonListFromDb`() =
        runTest {
            val expectedErrorTitle = R.string.error_title_no_internet
            val expectedErrorMessage = R.string.error_message_no_internet

            coEvery { fetchPokemonList() } coAnswers {
                delay(100)
                Failure.NetworkError(ErrorType.NoInternet)
            }

            viewModel = buildViewModel()

            viewModel.uiState.test {
                awaitItem() shouldBeEqualTo HomeUiState.Loading
                cancel()
            }
            advanceUntilIdle()

            viewModel.dialogState.value?.title shouldBeEqualTo expectedErrorTitle
            viewModel.dialogState.value?.message shouldBeEqualTo expectedErrorMessage

            viewModel.getPokemonList()

            viewModel.uiState.test {
                extractPagingItems(
                    (awaitItem() as HomeUiState.Success).pokemonList
                ) { items ->
                    items.size shouldBeEqualTo 10
                    items shouldBeEqualTo expectedPokemonList
                }
                cancel()
            }
        }

    private suspend fun <T : Any> extractPagingItems(
        pagingData: Flow<PagingData<T>>,
        onResult: (List<T>) -> Unit,
    ) {
        val lazyPagingItems = LazyPagingItemsTest(
            pagingData,
            mainDispatcherRule.testDispatcher
        )
        lazyPagingItems.initPagingDiffer()
        onResult(lazyPagingItems.itemSnapshotList.items)
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
