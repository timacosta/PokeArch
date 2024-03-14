package com.architects.pokearch.ui.features.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.di.annotations.IO
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.ui.components.pagingsource.PokemonPagingSourceFlowBuilder
import com.architects.pokearch.ui.features.home.state.HomeUiState
import com.architects.pokearch.ui.mapper.DialogData
import com.architects.pokearch.ui.mapper.ErrorDialogManager
import com.architects.pokearch.usecases.FetchPokemonList
import com.architects.pokearch.usecases.GetPokemonList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPokemonList: GetPokemonList,
    private val fetchPokemonList: FetchPokemonList,
    @IO private val dispatcher: CoroutineDispatcher,
    private val errorDialogManager: ErrorDialogManager,
    private val pokemonPagingFlowBuilder: PokemonPagingSourceFlowBuilder,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _dialogState: MutableStateFlow<DialogData?> = MutableStateFlow(null)
    val dialogState: StateFlow<DialogData?> = _dialogState

    private val _afterDbCallState = MutableStateFlow(false)
    val afterDbCallState: StateFlow<Boolean> = _afterDbCallState

    init {
        fetchData()
    }

    private fun fetchData(pokemonName: String = "") {

        viewModelScope.launch {
            when (val failure = withContext(dispatcher) { fetchPokemonList() }) {
                null -> {
                    _uiState.update {
                        HomeUiState.Success(
                            pokemonPagingFlowBuilder(pokemonName, getPokemonList, viewModelScope)
                        )
                    }
                }
                else -> {
                    when (failure) {
                        is Failure.NetworkError -> {
                            showErrorDialog(failure)
                        }

                        Failure.UnknownError -> {
                            showScreenError(failure)
                        }
                    }
                }
            }
        }
    }

    fun getPokemonListFromDb(pokemonName: String = "") {
        _uiState.update {
            HomeUiState.Success(
                pokemonPagingFlowBuilder(pokemonName, getPokemonList, viewModelScope)
            )
        }
        _afterDbCallState.update { true }
    }

    private fun showScreenError(failure: Failure) {
        _uiState.update {
            HomeUiState.Error(failure)
        }
    }

    private fun showErrorDialog(failure: Failure.NetworkError) {
        _dialogState.update {
            errorDialogManager.transform(
                errorType = failure.errorType,
                onDismiss = { onDialogDismiss() }
            )
        }
    }

    private fun onDialogDismiss() {
        _dialogState.update { null }
        getPokemonListFromDb()
    }
}
