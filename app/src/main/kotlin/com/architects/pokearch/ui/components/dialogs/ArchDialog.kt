package com.architects.pokearch.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.architects.pokearch.ui.mapper.DialogData

@Composable
fun ArchDialog(
    data: DialogData,
) {
    AlertDialog(
        onDismissRequest = {
            data.onDissmiss()
        },
        title = {
            Text(text = stringResource(id = data.title))
        },
        text = {
            Text(text = stringResource(id = data.message))
        },
        confirmButton = {},
        dismissButton = {}
    )
}
