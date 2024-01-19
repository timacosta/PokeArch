package com.architects.pokearch.ui.features

import com.architects.pokearch.domain.model.error.ErrorType

class ErrorDialogMapper {
    fun transform(
        errorType: ErrorType,
        onDismiss: () -> Unit,
    ): DialogData =
        when (errorType) {
            is ErrorType.NoInternet -> DialogData(
                title = "No Internet",
                message = "Check your internet connection",
                onDissmiss = onDismiss
            )

            is ErrorType.TimeOut -> DialogData(
                title = "Server not responding",
                message = "Try again later",
                onDissmiss = onDismiss
            )

            is ErrorType.BadRequest -> DialogData(
                title = "Bad Request",
                message = "Try again later",
                onDissmiss = onDismiss
            )

            is ErrorType.InternalServerError -> DialogData(
                title = "Internal Server Error",
                message = "Try again later",
                onDissmiss = onDismiss
            )

            is ErrorType.BadGateway -> DialogData(
                title = "Bad Gateway",
                message = "Try again later",
                onDissmiss = onDismiss
            )

            is ErrorType.ServiceUnavailable -> DialogData(
                title = "Service Unavailable",
                message = "Try again later",
                onDissmiss = onDismiss
            )

            is ErrorType.GatewayTimeout -> DialogData(
                title = "Gateway Timeout",
                message = "Try again later",
                onDissmiss = onDismiss
            )

            is ErrorType.NotFound -> DialogData(
                title = "Not Found",
                message = "Try again later",
                onDissmiss = onDismiss
            )

            is ErrorType.Unauthorized -> DialogData(
                title = "Unauthorized",
                message = "Try again later",
                onDissmiss = onDismiss
            )

            else -> DialogData(
                title = "Unknown Error",
                message = "Try again later",
                onDissmiss = onDismiss
            )
        }
}

data class DialogData(
    val title: String,
    val message: String,
    val onDissmiss: () -> Unit,
)