package com.architects.pokearch.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PokemonEntity (
    @PrimaryKey
    val id: Int,
    val name: String,
    val url: String,
)
