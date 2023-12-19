package com.architects.pokearch.core.framework.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity (
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String,
)
