package com.architects.pokearch.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
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
    fun init() { hiltRule.inject() }

    @Test
    fun foo() {
        composeRule.onNodeWithText("Bulbasaur").assertIsDisplayed()
    }
}