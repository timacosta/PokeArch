package com.architects.pokearch.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architects.pokearch.core.model.Pokemon

@Entity
data class PokemonEntity (
    @PrimaryKey
    val name: String,
    val url: String,
)

fun Pokemon.asPokemonInfo() = PokemonEntity(name, url)
