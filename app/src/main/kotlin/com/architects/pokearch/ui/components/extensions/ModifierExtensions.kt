package com.architects.pokearch.ui.components.extensions

import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier

@Stable
fun Modifier.modifyIf(condition: Boolean, newModifier: (Modifier) -> Modifier): Modifier {
    return if (condition) {
        this.then(newModifier(this))
    } else {
        this
    }
}