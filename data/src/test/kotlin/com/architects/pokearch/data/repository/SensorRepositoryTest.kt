package com.architects.pokearch.data.repository

import com.architects.pokearch.data.datasource.SensorSource
import com.architects.pokearch.testing.rules.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class SensorRepositoryTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `GIVEN getAccelerometerValue WHEN invokes THEN call correct method from sensorSource`() = runTest {
        val sensorSource: SensorSource = mockk(relaxed = true)
        val repository = buildRepository(sensorSource)

        repository.getAccelerometerValue()

        coEvery { sensorSource.setOnSensorValuesChangedListener(any()) } just runs
    }

    private fun buildRepository(
        sensorSource: SensorSource
    ) = SensorRepository(
        accelerometerSensor = sensorSource
    )
}
