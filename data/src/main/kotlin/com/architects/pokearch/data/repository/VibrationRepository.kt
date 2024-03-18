package com.architects.pokearch.data.repository

import com.architects.pokearch.data.datasource.VibrationSource
import com.architects.pokearch.domain.repository.VibrationRepositoryContract
import javax.inject.Inject

class VibrationRepository @Inject constructor(
    private val vibrationSource: VibrationSource,
) : VibrationRepositoryContract {
    override fun vibrate() = vibrationSource.vibrate()
}
