package com.architects.pokearch.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasScrollAction
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.compose.ui.test.performTextInput
import com.architects.pokearch.PokeArchActivity
import com.architects.pokearch.remote.MockWebServerRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class PokeArchAppTest {

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
    fun searchBarWorksAsExpected() {

        composeRule
            .onNodeWithTag("topBar")
            .performClick()

        composeRule
            .onNodeWithTag("searchField")
            .performTextInput("Bulbasaur")

        composeRule
            .onNode(
                hasTestTag("homeItem")
                        and hasText("Bulbasaur")
            )
            .assertIsDisplayed()
    }

    @Test
    fun performScrollAndShowMorePokemons() {
        composeRule.onNode(hasScrollAction()).performScrollToIndex(5)
        composeRule.onNodeWithText("Charmeleon").assertIsDisplayed()

        composeRule.onNode(hasScrollAction()).performScrollToIndex(5)
        composeRule.onNodeWithText("Blastoise").assertIsDisplayed()
    }

    @Test
    fun bottomBarItemsIsVisible() {
        composeRule.onNodeWithText("Home").assertIsDisplayed()
        composeRule.onNodeWithText("Team").assertIsDisplayed()
        composeRule.onNodeWithText("Shake'n'Catch").assertIsDisplayed()
    }

    @Test
    fun homeBottomBarItemIsSelected() {
        composeRule.onNodeWithText("Home")
            .assertIsDisplayed()
            .assertIsSelected()
    }

    @Test
    fun navigateToTeamScreen() {
        composeRule.onNodeWithText("Team").performClick()

        composeRule.onNodeWithText("Team").assertIsDisplayed()
    }

    @Test
    fun navigateToShakeNCatchScreen() {
        composeRule.onNodeWithText("Shake'n'Catch").performClick()

        composeRule.onNodeWithText("Shake'n'Catch").assertIsDisplayed()
    }

    @Test
    fun navigateToDetailScreen() {
        composeRule.onNodeWithText("Bulbasaur").performClick()

        composeRule.onNodeWithText("Grass").assertIsDisplayed()
        composeRule.onNodeWithText("Poison").assertIsDisplayed()
    }
}