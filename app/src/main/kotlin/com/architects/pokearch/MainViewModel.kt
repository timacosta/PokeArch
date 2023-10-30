package com.architects.pokearch

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class MainViewModel(app: Application): AndroidViewModel(app) {

    private val _screenIndex = mutableStateOf(0)
    val screenIndex = _screenIndex

    fun onIndexChange(index: Int) {
        _screenIndex.value = index
    }
}