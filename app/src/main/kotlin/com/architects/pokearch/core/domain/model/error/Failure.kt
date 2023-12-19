package com.architects.pokearch.core.domain.model.error

sealed class Failure {
    data object UnknownError: Failure()
}
