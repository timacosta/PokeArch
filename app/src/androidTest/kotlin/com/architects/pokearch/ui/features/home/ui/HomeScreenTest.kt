package com.architects.pokearch.ui.features.home.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest //Check Automation tests for Hilt
class HomeScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun displaysPokemonNameWhenProvided() {
        val pokemonName = "Pikachu"

        composeRule.setContent { HomeScreen(pokemonName = pokemonName, onNavigationClick = {}) }

        composeRule.onNodeWithText(pokemonName).assertIsDisplayed()
    }
}