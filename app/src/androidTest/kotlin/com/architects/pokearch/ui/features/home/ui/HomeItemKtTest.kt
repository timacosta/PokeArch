package com.architects.pokearch.ui.features.home.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.architects.pokearch.domain.model.Pokemon
import org.junit.Rule
import org.junit.Test


class HomeItemTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun displaysPokemonName() {
        val pokemon = Pokemon(name = "Pikachu", url = "http://example.com/pikachu.png")

        composeRule.setContent { HomeItem(pokemon = pokemon, onItemClick = {}) }

        composeRule.onNodeWithText("Pikachu").assertIsDisplayed()
    }

    @Test
    fun onItemClickIsCalledWhenTtemIsClicked() {
        var clicked = false
        val pokemon = Pokemon(name = "Pikachu", url = "http://example.com/pikachu.png")

        composeRule.setContent { HomeItem(pokemon = pokemon, onItemClick = { clicked = true }) }

        composeRule.onNodeWithText("Pikachu").performClick()

        assert(clicked)
    }
}