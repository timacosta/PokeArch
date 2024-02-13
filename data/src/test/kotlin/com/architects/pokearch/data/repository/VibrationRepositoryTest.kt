package com.architects.pokearch.data.repository

import com.architects.pokearch.data.datasource.VibrationSource
import com.architects.pokearch.testing.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class VibrationRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN startVibration WHEN invokes THEN call correct method from vibrationSource`() = runTest {
        val vibrationSource: VibrationSource = mockk(relaxed = true)
        val vibrationRepository = buildRepository(vibrationSource)
        coEvery { vibrationSource.vibrate() } returns Unit

        vibrationRepository.vibrate()

        verify { vibrationSource.vibrate() }
    }

    private fun buildRepository(
        vibrationSource: VibrationSource
    ) = VibrationRepository(
        vibrationSource = vibrationSource
    )

}