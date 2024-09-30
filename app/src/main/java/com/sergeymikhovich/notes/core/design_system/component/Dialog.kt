package com.sergeymikhovich.notes.core.design_system.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

data class AlertDialogState(
    val title: String,
    val description: String,
    val confirmButtonText: String,
    val dismissButtonText: String
)

@Composable
fun AlertDialogWithState(
    state: AlertDialogState,
    onReply: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    with(state) {
        AlertDialog(
            modifier = modifier,
            onDismissRequest = {
                onReply(false)
            },
            title = {
                Text(text = title)
            },
            text = {
                Text(text = description)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onReply(true)
                    }
                ) {
                    Text(text = confirmButtonText)
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onReply(false)
                    }
                ) {
                    Text(text = dismissButtonText)
                }
            }
        )
    }
}