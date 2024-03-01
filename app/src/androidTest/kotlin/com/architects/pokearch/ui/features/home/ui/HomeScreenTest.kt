package com.architects.pokearch.ui.features.home.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.architects.pokearch.ui.features.home.viewModel.HomeViewModel
import com.architects.pokearch.ui.mapper.ErrorDialogManager
import com.architects.pokearch.usecases.FetchPokemonList
import com.architects.pokearch.usecases.GetPokemonList
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createComposeRule()

    val coroutineScope = CoroutineScope(Dispatchers.Unconfined)

    private val getPokemonList: GetPokemonList = mockk(relaxed = true)
    private val fetchPokemonList: FetchPokemonList = mockk(relaxed = true)
    private val errorDialogManager: ErrorDialogManager = mockk(relaxed = true)

//    @Before
//    fun init() {
//        hiltRule.inject()
//    }

    @Test
    fun displaysPokemonNameWhenProvided() = runTest{
        val pokemonName = "Pikachu"
        val viewModel = buildViewModel()

        viewModel.getPokemonList(pokemonName)

        composeRule.setContent { HomeScreen(viewModel = viewModel, pokemonName = pokemonName, onNavigationClick = {}) }

        composeRule.onNodeWithText(pokemonName).assertIsDisplayed()
    }

    private fun buildViewModel() = HomeViewModel(
        getPokemonList = getPokemonList,
        fetchPokemonList = fetchPokemonList,
        dispatcher = Dispatchers.Unconfined,
        errorDialogManager = ErrorDialogManager()
    )
}