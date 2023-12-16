package com.architects.pokearch.ui.components.extensions

import androidx.compose.ui.graphics.Color
import com.architects.pokearch.ui.theme.LightBlue
import com.architects.pokearch.ui.theme.TypeBug
import com.architects.pokearch.ui.theme.TypeDark
import com.architects.pokearch.ui.theme.TypeDragon
import com.architects.pokearch.ui.theme.TypeElectric
import com.architects.pokearch.ui.theme.TypeFairy
import com.architects.pokearch.ui.theme.TypeFighting
import com.architects.pokearch.ui.theme.TypeFire
import com.architects.pokearch.ui.theme.TypeFlying
import com.architects.pokearch.ui.theme.TypeGhost
import com.architects.pokearch.ui.theme.TypeGrass
import com.architects.pokearch.ui.theme.TypeGround
import com.architects.pokearch.ui.theme.TypeIce
import com.architects.pokearch.ui.theme.TypeNormal
import com.architects.pokearch.ui.theme.TypePoison
import com.architects.pokearch.ui.theme.TypePsychic
import com.architects.pokearch.ui.theme.TypeRock
import com.architects.pokearch.ui.theme.TypeSteel
import com.architects.pokearch.ui.theme.TypeWater


fun String.abilityColor(): Color = when(this) {
    "Normal" -> TypeNormal
    "Fighting" -> TypeFighting
    "Flying" -> TypeFlying
    "Poison" -> TypePoison
    "Ground" -> TypeGround
    "Rock" -> TypeRock
    "Bug" -> TypeBug
    "Ghost" -> TypeGhost
    "Steel" -> TypeSteel
    "Fire" -> TypeFire
    "Water" -> TypeWater
    "Grass" -> TypeGrass
    "Electric" -> TypeElectric
    "Psychic" -> TypePsychic
    "Ice" -> TypeIce
    "Dragon" -> TypeDragon
    "Fairy" -> TypeFairy
    "Dark" -> TypeDark
    else -> LightBlue
}