package com.architects.pokearch.domain.model

import com.architects.pokearch.domain.model.Stat.Companion.ATK
import com.architects.pokearch.domain.model.Stat.Companion.DEF
import com.architects.pokearch.domain.model.Stat.Companion.HP
import com.architects.pokearch.domain.model.Stat.Companion.SPEED
import com.architects.pokearch.domain.model.Stat.Companion.SP_ATK
import com.architects.pokearch.domain.model.Stat.Companion.SP_DEF

private const val BASE_IMAGE_URL = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/"
private const val OFFICIAL_ARTWORK_URL = "pokemon/other/official-artwork/"
private const val SMALL_SPRITE_ARTWORK_URL = "pokemon/"

data class PokemonInfo(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val experience: Int,
    val types: List<Types>,
    val stats: List<Stats>,
    val team: Boolean = false,
) {
    fun getOfficialArtworkImageUrl(): String {
        return BASE_IMAGE_URL +
                OFFICIAL_ARTWORK_URL + "${id}.png"
    }

    fun getSmallSpriteArtworkImageUrl(): String {
        return BASE_IMAGE_URL +
                SMALL_SPRITE_ARTWORK_URL + "${id}.png"
    }

}

data class Types(
    val slot: Int,
    val type: Type,
)

data class Type(
    val name: String,
)

data class Stats(
    val value: Int,
    val stat: Stat,
) {
    companion object {
        const val maxHp = 300
        const val maxAttack = 300
        const val maxSpAttack = 300
        const val maxDefense = 300
        const val maxSpDefense = 300
        const val maxSpeed = 300
    }
    val maxValue: Int
        get() {
            return when (stat.name) {
                HP -> maxHp
                ATK -> maxAttack
                DEF -> maxDefense
                SP_ATK -> maxSpAttack
                SP_DEF -> maxSpDefense
                SPEED -> maxSpeed
                else -> value
            }
        }
}

data class Stat(
    val name: String,
) {
    companion object {
        const val HP = "HP"
        const val ATK = "Atk"
        const val DEF = "Def"
        const val SP_ATK = "Sp. Atk"
        const val SP_DEF = "Sp. Def"
        const val SPEED = "Speed"
    }
}
