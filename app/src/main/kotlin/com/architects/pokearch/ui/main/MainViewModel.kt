package com.architects.pokearch.ui.main

import androidx.lifecycle.ViewModel
import com.architects.pokearch.ui.main.state.SearchWidgetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {

    private val _searchWidgetState: MutableStateFlow<SearchWidgetState>
    = MutableStateFlow(value = SearchWidgetState.CLOSED)

    val searchWidgetState: StateFlow<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableStateFlow<String> =
        MutableStateFlow(value = "")

    val searchTextState: StateFlow<String> = _searchTextState

    private val _searchBarIsExpandedState = MutableStateFlow(value = true)
    val searchBarIsExpandedState: StateFlow<Boolean> = _searchBarIsExpandedState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }
    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun updateSearchBarIsExpandedState(newValue: Boolean) {
        _searchBarIsExpandedState.update {
            !newValue
        }
    }
}
