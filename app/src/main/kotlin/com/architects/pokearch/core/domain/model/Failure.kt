package com.architects.pokearch.core.domain.model

sealed class Failure {
    data object UnknownError: Failure()
}
