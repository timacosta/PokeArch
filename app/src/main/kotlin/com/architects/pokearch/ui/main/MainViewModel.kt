package com.architects.pokearch.ui.main

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@Stable
data class MainUiState(
    val searchText: String = "",
    val isSearchIconVisible: Boolean = true,
    val isTopBarVisible: Boolean = true,
    val isBottomBarVisible: Boolean = true
)

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    fun updateSearchTextState(newValue: String) {
        _uiState.update {
            it.copy(
                searchText = newValue
            )
        }
    }
}

