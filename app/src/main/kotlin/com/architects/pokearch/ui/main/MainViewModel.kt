package com.architects.pokearch.ui.main

import androidx.lifecycle.ViewModel
import com.architects.pokearch.ui.main.MainUiState.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class MainUiState(
    val searchTextState: String = "",
    val isSearchBarExpanded: Boolean = true,
    val searchWidgetState: SearchWidgetState = SearchWidgetState.CLOSED
) {
    enum class SearchWidgetState {
        OPENED,
        CLOSED
    }
}

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _uiState.update {
            it.copy(
                searchWidgetState = newValue
            )
        }
    }

    fun updateSearchTextState(newValue: String) {
        _uiState.update {
            it.copy(
                searchTextState = newValue
            )
        }
    }

    fun updateSearchBarIsExpandedState(newValue: Boolean) {
        _uiState.update {
            it.copy(
                isSearchBarExpanded = newValue
            )
        }
    }
}

