package com.architects.pokearch.ui.components.pagingsource

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.architects.pokearch.usecases.GetPokemonList
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class PokemonPagingSourceFlowBuilder @Inject constructor() {
    operator fun invoke(
        pokemonName: String,
        getPokemonList: GetPokemonList,
        viewModelScope: CoroutineScope,
    ) = Pager(
        config = PagingConfig(pageSize = 20),
        initialKey = 0,
        pagingSourceFactory = {
            PokemonPagingSource(getPokemonList) { pokemonName }
        }
    ).flow.cachedIn(viewModelScope)
}
