package com.architects.pokearch.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architects.pokearch.core.model.PokemonInfo
import kotlin.random.Random

@Entity
data class PokemonInfoEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val experience: Int,
    val types: TypesHolder,
    val team: Boolean,

    val hp: Int = Random.nextInt(PokemonInfo.maxHp),
    val attack: Int = Random.nextInt(PokemonInfo.maxAttack),
    val defense: Int = Random.nextInt(PokemonInfo.maxDefense),
    val speed: Int = Random.nextInt(PokemonInfo.maxSpeed),
    val exp: Int = Random.nextInt(PokemonInfo.maxExp),
)

fun PokemonInfo.asPokemonInfoEntity() = PokemonInfoEntity(
    id,
    name,
    height,
    weight,
    experience,
    TypesHolder(types.map { it.asTypesEntity() }),
    team,
    hp,
    attack,
    defense,
    speed,
    exp
)
