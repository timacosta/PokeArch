package com.architects.pokearch.ui.components.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.architects.pokearch.domain.model.Pokemon
import com.architects.pokearch.usecases.GetPokemonList

class PokemonPagingSource(
    private val getPokemonList: GetPokemonList,
    private val filter: () -> String,
) : PagingSource<Int, Pokemon>() {

    override fun getRefreshKey(state: PagingState<Int, Pokemon>): Int? =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pokemon> {
        val currentPageNumber = params.key ?: 0

        val pokemons = getPokemonList(filter(), currentPageNumber)
        val nextPokemons = getPokemonList(filter(), currentPageNumber + 1)

        val prevkey = prevKey(currentPageNumber)

        val nextKey = nextKey(nextPokemons, currentPageNumber)

        return LoadResult.Page(
            prevKey = prevkey,
            nextKey = nextKey,
            data = pokemons
        )
    }

    private fun prevKey(currentPageNumber: Int) = when {
        currentPageNumber > 0 -> currentPageNumber - 1
        else -> null
    }

    private fun nextKey(
        nextPokemons: List<Pokemon>,
        currentPageNumber: Int,
    ) = when {
        nextPokemons.isNotEmpty() -> currentPageNumber + 1
        else -> null
    }
}
