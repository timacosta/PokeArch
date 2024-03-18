package com.architects.pokearch.domain.model.error

sealed class Failure {
    data object UnknownError : Failure()
    data class NetworkError(val errorType: ErrorType) : Failure()
}
