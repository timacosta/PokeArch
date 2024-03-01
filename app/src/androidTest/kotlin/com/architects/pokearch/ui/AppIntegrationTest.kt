package com.architects.pokearch.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import arrow.core.Either
import com.architects.pokearch.PokeArchActivity
import com.architects.pokearch.core.framework.network.PokemonServerDataSource
import com.architects.pokearch.core.framework.network.service.PokedexService
import com.architects.pokearch.domain.model.PokemonInfo
import com.architects.pokearch.remote.MockWebServerRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeInstanceOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class AppIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<PokeArchActivity>()



    @Before
    fun init() { hiltRule.inject() }

    @Inject
    lateinit var remoteDataSource: PokemonServerDataSource

    @Inject
    lateinit var pokemonService: PokedexService

    @Test
    fun testThatMockWebServerWorks() = runTest {
        val result = remoteDataSource.getPokemon(1)
        result.shouldBeInstanceOf<Either.Right<PokemonInfo>>()
    }

    @Test
    fun foo() {
        composeRule.onNodeWithText("Bulbasaur").assertIsDisplayed()
    }
}