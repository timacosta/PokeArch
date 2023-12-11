package com.architects.pokearch

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.architects.pokearch.ui.PokeArchApp
import com.architects.pokearch.ui.theme.PokeArchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokeArchActivity : ComponentActivity() {

    companion object{
        private const val START_DELAY = 1400L
        private const val DURATION = 600L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        exitAnimationSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            PokeArchScreen {
                PokeArchApp()
            }
        }
    }

    private fun exitAnimationSplashScreen(){
        installSplashScreen()
            .setOnExitAnimationListener { splashScreenView ->
                val fadeOut = ObjectAnimator.ofFloat(
                    splashScreenView.view,
                    View.TRANSLATION_Y,
                    0f,
                    -splashScreenView.view.height.toFloat()
                )
                with(fadeOut){
                    interpolator = AnticipateInterpolator()
                    startDelay = START_DELAY
                    duration = DURATION
                    doOnEnd { splashScreenView.remove() }
                    start()
                }
            }
    }
}
