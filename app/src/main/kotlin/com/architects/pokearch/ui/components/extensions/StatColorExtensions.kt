package com.architects.pokearch.ui.components.extensions

import androidx.compose.ui.graphics.Color
import com.architects.pokearch.ui.theme.AtkColor
import com.architects.pokearch.ui.theme.DefColor
import com.architects.pokearch.ui.theme.HPColor
import com.architects.pokearch.ui.theme.LightBlue
import com.architects.pokearch.ui.theme.SpAtkColor
import com.architects.pokearch.ui.theme.SpDefColor
import com.architects.pokearch.ui.theme.SpdColor


fun String.statColor(): Color = when(this) {
    "HP" -> HPColor
    "Atk" -> AtkColor
    "Def" -> DefColor
    "Sp. Atk" -> SpAtkColor
    "Sp. Defense" -> SpDefColor
    "Speed" -> SpdColor
    else -> LightBlue
}
