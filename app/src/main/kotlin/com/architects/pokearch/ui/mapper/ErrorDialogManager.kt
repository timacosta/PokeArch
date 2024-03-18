package com.architects.pokearch.ui.mapper

import androidx.annotation.StringRes
import com.architects.pokearch.R
import com.architects.pokearch.domain.model.error.ErrorType

class ErrorDialogManager {
    fun transform(
        errorType: ErrorType,
        onDismiss: () -> Unit,
    ): DialogData =
        when (errorType) {
            is ErrorType.NoInternet -> DialogData(
                title = R.string.error_title_no_internet,
                message = R.string.error_message_no_internet,
                onDissmiss = onDismiss
            )

            is ErrorType.TimeOut -> DialogData(
                title = R.string.error_title_timeout,
                message = R.string.error_message_default,
                onDissmiss = onDismiss
            )

            is ErrorType.BadRequest -> DialogData(
                title = R.string.error_title_bad_request,
                message = R.string.error_message_default,
                onDissmiss = onDismiss
            )

            is ErrorType.InternalServerError -> DialogData(
                title = R.string.error_title_internal_server_error,
                message = R.string.error_message_default,
                onDissmiss = onDismiss
            )

            is ErrorType.BadGateway -> DialogData(
                title = R.string.error_title_bad_gateway,
                message = R.string.error_message_default,
                onDissmiss = onDismiss
            )

            is ErrorType.ServiceUnavailable -> DialogData(
                title = R.string.error_title_service_unavailable,
                message = R.string.error_message_default,
                onDissmiss = onDismiss
            )

            is ErrorType.GatewayTimeout -> DialogData(
                title = R.string.error_title_gateway_timeout,
                message = R.string.error_message_default,
                onDissmiss = onDismiss
            )

            is ErrorType.NotFound -> DialogData(
                title = R.string.error_title_not_found,
                message = R.string.error_message_default,
                onDissmiss = onDismiss
            )

            is ErrorType.Unauthorized -> DialogData(
                title = R.string.error_title_unauthorized,
                message = R.string.error_message_default,
                onDissmiss = onDismiss
            )

            else -> DialogData(
                title = R.string.error_title_unknown,
                message = R.string.error_message_default,
                onDissmiss = onDismiss
            )
        }
}

data class DialogData(
    @StringRes val title: Int,
    @StringRes val message: Int,
    val onDissmiss: () -> Unit,
)
