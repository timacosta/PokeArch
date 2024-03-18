package com.architects.pokearch.data.mapper

import com.architects.pokearch.domain.model.error.ErrorType

@Suppress("MagicNumber")
object ErrorMapper {
    fun getErrorType(code: Int): ErrorType = when (code) {
        400 -> ErrorType.BadRequest
        401 -> ErrorType.Unauthorized
        404 -> ErrorType.NotFound
        408 -> ErrorType.TimeOut
        500 -> ErrorType.InternalServerError
        502 -> ErrorType.BadGateway
        503 -> ErrorType.ServiceUnavailable
        504 -> ErrorType.GatewayTimeout
        else -> ErrorType.Unknown
    }
}
