package com.architects.pokearch.ui.features

import com.architects.pokearch.domain.model.error.ErrorType

class ErrorDialogMapper {
    fun transform(
        errorType: ErrorType,
        onDissmiss: () -> Unit,
    ): DialogData =
        when (errorType) {
            is ErrorType.NoInternet -> DialogData(
                title = "No Internet",
                message = "Check your internet connection",
                onDissmiss = onDissmiss
            )

            is ErrorType.TimeOut -> DialogData(
                title = "Server not responding",
                message = "Try again later",
                onDissmiss = onDissmiss
            )

            is ErrorType.BadRequest -> DialogData(
                title = "Bad Request",
                message = "Try again later",
                onDissmiss = onDissmiss
            )

            is ErrorType.InternalServerError -> DialogData(
                title = "Internal Server Error",
                message = "Try again later",
                onDissmiss = onDissmiss
            )

            is ErrorType.BadGateway -> DialogData(
                title = "Bad Gateway",
                message = "Try again later",
                onDissmiss = onDissmiss
            )

            is ErrorType.ServiceUnavailable -> DialogData(
                title = "Service Unavailable",
                message = "Try again later",
                onDissmiss = onDissmiss
            )

            is ErrorType.GatewayTimeout -> DialogData(
                title = "Gateway Timeout",
                message = "Try again later",
                onDissmiss = onDissmiss
            )

            is ErrorType.NotFound -> DialogData(
                title = "Not Found",
                message = "Try again later",
                onDissmiss = onDissmiss
            )

            is ErrorType.Unauthorized -> DialogData(
                title = "Unauthorized",
                message = "Try again later",
                onDissmiss = onDissmiss
            )

            else -> DialogData(
                title = "Unknown Error",
                message = "Try again later",
                onDissmiss = onDissmiss
            )
        }
}

data class DialogData(
    val title: String,
    val message: String,
    val onDissmiss: () -> Unit,
)