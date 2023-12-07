package com.architects.pokearch.ui.components.extensions

import androidx.compose.ui.graphics.Color
import com.architects.pokearch.ui.theme.AtkColor
import com.architects.pokearch.ui.theme.DefColor
import com.architects.pokearch.ui.theme.HPColor
import com.architects.pokearch.ui.theme.LightBlue
import com.architects.pokearch.ui.theme.SpAtkColor
import com.architects.pokearch.ui.theme.SpDefColor
import com.architects.pokearch.ui.theme.SpdColor
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


fun String.statColor(): Color = when(this) {
    "HP" -> HPColor
    "Atk" -> AtkColor
    "Def" -> DefColor
    "Sp. Atk" -> SpAtkColor
    "Sp. Defense" -> SpDefColor
    "Speed" -> SpdColor
    else -> LightBlue
}