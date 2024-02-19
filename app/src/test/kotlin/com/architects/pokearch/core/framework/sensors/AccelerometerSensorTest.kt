package com.architects.pokearch.core.framework.sensors

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorManager
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class AccelerometerSensorTest {

    private val context: Context = mockk(relaxed = true)

    @Test
    fun `GIVEN Accelerometer Sensor WHEN available THEN doesSensorExist return true`() = runTest {
        val accelerometerSensor = buildAccelerometerSensor()

        every { context.packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER) } returns true

        accelerometerSensor.doesSensorExist shouldBeEqualTo true
    }

    @Test
    fun `GIVEN Accelerometer Sensor WHEN not available THEN doesSensorExist return false`() = runTest {
        val accelerometerSensor = buildAccelerometerSensor()

        every { context.packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER) } returns false

        accelerometerSensor.doesSensorExist shouldBeEqualTo false
    }

    @Test
    fun `GIVEN Accelerometer Sensor WHEN startListening THEN sensor is registered`() = runTest {
        val sensorManager: SensorManager = mockk(relaxed = true)
        val sensor: Sensor = mockk(relaxed = true)

        every { context.getSystemService(Context.SENSOR_SERVICE) } returns sensorManager

        every { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) } returns sensor

        every { context.packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER) } returns true

        val accelerometerSensor = buildAccelerometerSensor()

        accelerometerSensor.startListening()

        verify { sensorManager.registerListener(accelerometerSensor, sensor, SensorManager.SENSOR_DELAY_NORMAL) }
    }

    @Test
    fun `GIVEN Accelerometer Sensor WHEN stopListening THEN sensor is unregistered`() = runTest {
        val sensorManager: SensorManager = mockk(relaxed = true)
        val sensor: Sensor = mockk(relaxed = true)

        every { context.getSystemService(Context.SENSOR_SERVICE) } returns sensorManager

        every { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) } returns sensor

        every { context.packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_ACCELEROMETER) } returns true

        val accelerometerSensor = buildAccelerometerSensor()

        accelerometerSensor.startListening()
        accelerometerSensor.stopListening()

        verify { sensorManager.unregisterListener(accelerometerSensor) }
    }

    private fun buildAccelerometerSensor() = AccelerometerSensor(
        context = context
    )

}