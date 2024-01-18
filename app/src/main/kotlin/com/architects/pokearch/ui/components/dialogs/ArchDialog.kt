package com.architects.pokearch.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.architects.pokearch.ui.features.DialogData

@Composable
fun ArchDialog(
    data: DialogData,
) {
    AlertDialog(
        onDismissRequest = {
            data.onDissmiss()
        },
        title = {
            Text(text = data.title)
        },
        text = {
            Text(text = data.message)
        },
        confirmButton = {},
        dismissButton = {}
    )
}