package com.architects.pokearch.ui.screens.feature

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FeatureScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "Feature Screen")
    }

}