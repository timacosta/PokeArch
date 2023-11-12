package com.architects.pokearch.core.data.repository

import arrow.core.Either
import com.architects.pokearch.core.data.database.dao.PokemonDao
import com.architects.pokearch.core.data.database.dao.PokemonInfoDao
import com.architects.pokearch.core.data.database.entities.asPokemonInfo
import com.architects.pokearch.core.data.database.entities.asPokemonInfoEntity
import com.architects.pokearch.core.data.network.service.PokedexService
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import com.architects.pokearch.core.model.Failure
import com.architects.pokearch.core.model.Pokemon
import com.architects.pokearch.core.model.PokemonInfo
import com.architects.pokearch.core.model.asPokemon
import com.architects.pokearch.core.model.asPokemonInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PokeArchRepository(
    private val pokedexService: PokedexService,
    private val pokemonDao: PokemonDao,
    private val pokemonInfoDao: PokemonInfoDao,

    ) : PokeArchRepositoryContract {

    companion object {
        private const val LIMIT_ALL = 10000
    }

    override suspend fun fetchPokemonList(
        filter: String,
        page: Int,
        limit: Int,
    ): Flow<Either<Failure, List<Pokemon>>> = flow {
        val offset = page * limit
        var pokemons = pokemonDao.getPokemonList(filter, limit, offset).map { it.asPokemon() }

        if (pokemons.size < limit) {
            val response = if (filter.isEmpty()) {
                pokedexService.fetchPokemonList(limit, offset)
            } else {
                pokedexService.fetchPokemonList(LIMIT_ALL, 0)
            }

            if (response.isSuccessful) {
                response.body()?.let { pokemonResponse ->
                    pokemonDao.insertPokemonList(pokemonResponse.results.map { it.asPokemonInfo() })
                    pokemons = pokemonDao.getPokemonList(filter, limit, offset).map { it.asPokemon() }
                    emit(Either.Right(pokemons))
                }
            } else if (pokemons.isNotEmpty()) {
                emit(Either.Right(pokemons))
            } else {
                emit(Either.Left(Failure.UnknownError))
            }
        }else{
            emit(Either.Right(pokemons))
        }
    }

    override suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>> = flow {

        val pokemon = pokemonInfoDao.getPokemonInfo(id)

        if (pokemon == null){
            val response = pokedexService.fetchPokemonInfo(id)

            if (response.isSuccessful) {
                response.body()?.let {
                    pokemonInfoDao.insertPokemonInfo(it.asPokemonInfoEntity())
                    emit(Either.Right(it))
                }
            }else{
                emit(Either.Left(Failure.UnknownError))
            }
        }else{
            emit(Either.Right(pokemon.asPokemonInfo()))
        }
    }
}
