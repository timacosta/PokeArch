package com.architects.pokearch.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.architects.pokearch.PokeArchActivity
import com.architects.pokearch.remote.MockWebServerRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class AppIntegrationTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val mockWebServerRule = MockWebServerRule()

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<PokeArchActivity>()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun firstSixPokemonsAreVisible() {
        composeRule.onNodeWithText("Bulbasaur").assertIsDisplayed()
        composeRule.onNodeWithText("Ivysaur").assertIsDisplayed()
        composeRule.onNodeWithText("Venusaur").assertIsDisplayed()
        composeRule.onNodeWithText("Charmander").assertIsDisplayed()
        composeRule.onNodeWithText("Charmeleon").assertIsDisplayed()
        composeRule.onNodeWithText("Charizard").assertIsDisplayed()
    }

    @Test
    fun navigateToDetailScreen() {
        composeRule.onNodeWithText("Bulbasaur").performClick()

        composeRule.onNodeWithText("Grass").assertIsDisplayed()
        composeRule.onNodeWithText("Poison").assertIsDisplayed()
    }



}