package com.architects.pokearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.architects.pokearch.ui.navigation.Navigation
import com.architects.pokearch.ui.theme.PokeArchTheme

class PokeArchActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PokeArchTheme {
                Navigation()
            }
        }
    }
}