package com.architects.pokearch.core.framework.sensors.di

import android.app.Application
import com.architects.pokearch.core.framework.sensors.AccelerometerSensor
import com.architects.pokearch.core.framework.sensors.SensorSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SensorModule {
    @Provides
    @Singleton
    fun provideAccelerometerSensor(app: Application): SensorSource {
        return AccelerometerSensor(app)
    }
}
