package com.architects.pokearch.core.model

sealed class Failure {
    data object UnknownError: Failure()
}
