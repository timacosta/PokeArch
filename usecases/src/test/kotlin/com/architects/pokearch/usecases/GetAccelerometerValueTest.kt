package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.SensorRepositoryContract
import com.architects.pokearch.testing.rules.MainDispatcherRule
import com.architects.pokearch.testing.verifications.coVerifyOnce
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBe
import org.junit.Rule
import org.junit.Test

class GetAccelerometerValueTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN GetAccelerometerValue WHEN invokes THEN calls repository getAccelerometerValue and returns expected result`() = runTest {
        val expectedResult = flowOf(1.0f, 0.0f, 2.2f)
        val repository: SensorRepositoryContract = mockk {
            coEvery { getAccelerometerValue() } returns expectedResult
        }
        val useCase = GetAccelerometerValue(repository)

        val result = useCase()

        coVerifyOnce { repository.getAccelerometerValue() }
        result shouldBe expectedResult
    }
}