package com.architects.pokearch.ui.features.home.state

import androidx.paging.PagingData
import com.architects.pokearch.core.domain.model.Failure
import com.architects.pokearch.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Error(val failure: Failure) : HomeUiState()
    data class Success(val pokemonList: Flow<PagingData<Pokemon>>) : HomeUiState()
    data object NoSearchResult: HomeUiState()
}
