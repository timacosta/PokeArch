package com.architects.pokearch.ui.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.architects.pokearch.ui.components.placeHolders.ErrorScreen
import com.architects.pokearch.ui.theme.PokeArchTheme
import org.junit.Rule
import org.junit.Test

class ErrorScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun errorDescriptionIsPresent() {
        composeRule.setContent {
            PokeArchTheme {
                ErrorScreen()
            }
        }

        composeRule.onNodeWithText("Something went wrong").assertIsDisplayed()
    }
}