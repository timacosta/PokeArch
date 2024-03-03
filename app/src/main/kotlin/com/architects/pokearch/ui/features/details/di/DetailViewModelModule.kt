package com.architects.pokearch.ui.features.details.di

import androidx.lifecycle.SavedStateHandle
import com.architects.pokearch.ui.features.details.di.annotations.PokemonId
import com.architects.pokearch.ui.navigation.NavArg
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class DetailViewModelModule {

    @Provides
    @ViewModelScoped
    @PokemonId
    fun providePokemonId(savedStateHandle: SavedStateHandle): Int =
        savedStateHandle.get<Int>(NavArg.PokemonId.key) ?: 0
}
