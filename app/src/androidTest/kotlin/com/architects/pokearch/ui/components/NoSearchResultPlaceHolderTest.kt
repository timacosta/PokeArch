package com.architects.pokearch.ui.components

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.architects.pokearch.ui.components.placeHolders.NoSearchResultPlaceHolder
import com.architects.pokearch.ui.theme.PokeArchTheme
import org.junit.Rule
import org.junit.Test

class NoSearchResultPlaceHolderTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun placeholderImageExists() {
        composeRule.setContent {
            PokeArchTheme {
                NoSearchResultPlaceHolder()
            }
        }

        composeRule
            .onNodeWithContentDescription("Your Pokemon search was super effective… at finding nothing. Maybe try a different name?")
            .assertExists()
    }

    @Test
    fun textExists() {
        composeRule.setContent {
            PokeArchTheme {
                NoSearchResultPlaceHolder()
            }
        }

        composeRule
            .onNodeWithText("Your Pokemon search was super effective… at finding nothing. Maybe try a different name?")
            .assertExists()
    }
}