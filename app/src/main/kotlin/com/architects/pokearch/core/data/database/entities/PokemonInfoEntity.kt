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
    val stats: StatsHolder,
    val team: Boolean,
)
