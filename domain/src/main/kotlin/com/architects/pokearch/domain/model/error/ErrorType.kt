package com.architects.pokearch.domain.model.error

sealed class ErrorType {
    data object Unknown : ErrorType()
    data object NoInternet : ErrorType()

    // 4XX
    data object BadRequest : ErrorType()
    data object Unauthorized : ErrorType()
    data object NotFound : ErrorType()
    data object TimeOut : ErrorType()

    // 5XX
    data object InternalServerError : ErrorType()
    data object BadGateway : ErrorType()
    data object ServiceUnavailable : ErrorType()
    data object GatewayTimeout : ErrorType()
}
