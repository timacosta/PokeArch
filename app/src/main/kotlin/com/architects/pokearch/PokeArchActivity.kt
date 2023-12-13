package com.architects.pokearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.architects.pokearch.ui.PokeArchApp
import com.architects.pokearch.ui.theme.PokeArchScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokeArchActivity : ComponentActivity() {

    companion object{
        private const val DELAY = 2000L
        private var showSplashScreen = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashScreen()
        setContent {
            PokeArchScreen {
                PokeArchApp()
            }
        }
    }

    private fun splashScreen(){
        installSplashScreen()
            .setKeepOnScreenCondition { showSplashScreen }
        delaySplashScreen()
    }

    private fun delaySplashScreen() {
        lifecycleScope.launch {
            delay(DELAY)
            showSplashScreen = false
        }
    }
}
