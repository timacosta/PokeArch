package com.architects.pokearch.ui.main

import androidx.lifecycle.ViewModel
import com.architects.pokearch.ui.main.state.SearchWidgetState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _searchWidgetState: MutableStateFlow<SearchWidgetState> = MutableStateFlow(value = SearchWidgetState.CLOSED)
    val searchWidgetState: StateFlow<SearchWidgetState> = _searchWidgetState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }
}
