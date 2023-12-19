package com.architects.pokearch.core.framework.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.architects.pokearch.core.framework.local.entities.converters.StatsHolder
import com.architects.pokearch.core.framework.local.entities.converters.TypesHolder

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
