package com.architects.pokearch.core.di

import com.architects.pokearch.ui.features.ErrorDialogMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped


@Module
@InstallIn(ViewModelComponent::class)
object AppModule {
    @Provides
    @ViewModelScoped
    fun providesErrorDialogMapper(): ErrorDialogMapper = ErrorDialogMapper()
}