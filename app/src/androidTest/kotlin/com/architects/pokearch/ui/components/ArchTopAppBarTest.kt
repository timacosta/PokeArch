package com.architects.pokearch.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.architects.pokearch.ui.components.topAppBar.ArchTopAppBar
import com.architects.pokearch.ui.components.topAppBar.ArchTopAppBarType
import com.architects.pokearch.ui.theme.PokeArchTheme
import org.junit.Rule
import org.junit.Test

class ArchTopAppBarTest {

    @get:Rule
    val composeRule = createComposeRule()

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun normalAppBarContainsAppName() {
        composeRule.setContent {
            PokeArchTheme {
                ArchTopAppBar(
                    text = "",
                    scrollBehavior = null,
                    archTopAppBarType = ArchTopAppBarType.NORMAL,
                    onBackButtonClicked = {},
                    onTextChange = {}
                )
            }
        }

        composeRule.onNodeWithText("PokeArch")
    }
}