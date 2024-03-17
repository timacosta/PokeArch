package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.VibrationRepositoryContract
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.verifications.coVerifyOnce
import io.mockk.coJustRun
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class VibrateTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN Vibrate WHEN invokes THEN calls repository vibrate`() = runTest {
        val repository: VibrationRepositoryContract = mockk {
            coJustRun { vibrate() }
        }
        val useCase = Vibrate(repository)

        useCase()

        coVerifyOnce { repository.vibrate() }
    }
}