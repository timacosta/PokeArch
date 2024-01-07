package com.architects.pokearch.usecases

import com.architects.pokearch.domain.repository.VibrationRepositoryContract
import javax.inject.Inject

class Vibrate @Inject constructor(
    private val vibrateRepository: VibrationRepositoryContract
){
    operator fun invoke() = vibrateRepository.vibrate()
}
