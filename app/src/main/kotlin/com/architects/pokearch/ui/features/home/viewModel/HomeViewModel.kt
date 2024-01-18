package com.architects.pokearch.ui.features.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.architects.pokearch.core.di.annotations.IO
import com.architects.pokearch.domain.model.error.Failure
import com.architects.pokearch.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.ui.components.pagingsource.PokemonPagingSource
import com.architects.pokearch.ui.features.DialogData
import com.architects.pokearch.ui.features.ErrorDialogMapper
import com.architects.pokearch.ui.features.home.state.HomeUiState
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
    private val errorDialogMapper: ErrorDialogMapper,
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState.Loading)
    val uiState: StateFlow<HomeUiState> = _uiState

    private val _dialogState: MutableStateFlow<DialogData?> = MutableStateFlow(null)
    val dialogState: StateFlow<DialogData?> = _dialogState

    init {
        viewModelScope.launch {
            when (val failure = withContext(dispatcher) { fetchPokemonList() }) {
                null -> getPokemonList()
                else -> {
                    _uiState.value = HomeUiState.Error(failure)
                    if (failure is Failure.NetworkError) _dialogState.value =
                        errorDialogMapper.transform(
                            errorType = failure.errorType,
                            onDissmiss = { _dialogState.update { null } }
                        )
                }
            }
        }
    }

    fun getPokemonList(pokemonName: String = "") {
        _uiState.value =
            HomeUiState.Success(
                PokemonPagingSource.getPager(pokemonName, getPokemonList, viewModelScope)
            )
    }
}

