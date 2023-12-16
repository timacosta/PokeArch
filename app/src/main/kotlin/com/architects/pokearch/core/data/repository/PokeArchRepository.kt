package com.architects.pokearch.core.data.repository

import android.util.Log
import arrow.core.Either
import com.architects.pokearch.core.data.local.database.dao.PokemonDao
import com.architects.pokearch.core.data.local.database.dao.PokemonInfoDao
import com.architects.pokearch.core.data.mappers.PokemonEntityMapper
import com.architects.pokearch.core.data.mappers.PokemonInfoEntityMapper
import com.architects.pokearch.core.data.network.PokedexNetwortkDataSource
import com.architects.pokearch.core.data.network.service.CryService
import com.architects.pokearch.core.domain.model.Failure
import com.architects.pokearch.core.domain.model.Pokemon
import com.architects.pokearch.core.domain.model.PokemonInfo
import com.architects.pokearch.core.domain.repository.PokeArchRepositoryContract
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PokeArchRepository @Inject constructor(
    private val pokedexNetworkDataSource: PokedexNetwortkDataSource,
    private val cryService: CryService,
    private val pokemonDao: PokemonDao,
    private val pokemonInfoDao: PokemonInfoDao,
) : PokeArchRepositoryContract {

    companion object {
        private const val LIMIT_ALL = 10000
        private const val PREFIX_URL = "https://play.pokemonshowdown.com/audio/cries/"
        private const val SUBFIX_URL = ".mp3"
    }

    override suspend fun fetchPokemonList(
        filter: String,
        page: Int,
        limit: Int,
    ): Flow<Either<Failure, List<Pokemon>>> = flow {
        val offset = page * limit
        emit(
            Either.Right(
                PokemonEntityMapper.asDomain(
                    pokemonDao.getPokemonList(
                        filter,
                        limit,
                        offset
                    )
                )
            )
        )

        if (pokedexNetworkDataSource.areMorePokemonAvailableRemote(pokemonDao.countPokemonList())) {
            emit(pokedexNetworkDataSource.getRemotePokemonList(filter, limit, offset))
        }
    }




    override suspend fun fetchPokemonInfo(id: Int): Flow<Either<Failure, PokemonInfo>> = flow {
        val pokemon = pokemonInfoDao.getPokemonInfo(id)?.let { pokemon ->
            emit(Either.Right(PokemonInfoEntityMapper.asDomain(pokemon)))
        }

        if (pokemon == null) {
            emit(pokedexNetworkDataSource.getRemotePokemon(id))
        }
    }

    override suspend fun fetchCry(name: String): String {
        var result = ""
        tryCatchCry(name) { result = it }
        if (name.contains("-") && result.isEmpty()){
            tryCatchCry(name.replace("-", "")) { result = it }
            tryCatchCry(name.split("-")[0]) { result = it }
        }
        return "$PREFIX_URL$result$SUBFIX_URL"
    }

    private suspend fun tryCatchCry(name: String, isSuccessful: (String) -> Unit) {
        try {
            val fetchCry = cryService.thereIsCry(name)
            if(fetchCry.isSuccessful) isSuccessful(name)
        } catch (e: Exception) {
            Log.e("CryException", e.stackTraceToString())
        }
    }

    override suspend fun randomPokemon(): Flow<Either<Failure, PokemonInfo>> {
        return fetchPokemonInfo(pokemonDao.randomId())
    }
}
