package com.sergeymikhovich.notes.core.permission

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

data class PermissionScreenState(
    val title: String,
    val rationale: String,
    val confirmButtonText: String,
    val dismissButtonText: String
)

@Composable
fun PermissionScreen(
    state: PermissionScreenState,
    onRationaleReply: (Boolean) -> Unit
) {
    with(state) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AlertDialog(
                onDismissRequest = {
                    onRationaleReply(false)
                },
                title = {
                    Text(text = title)
                },
                text = {
                    Text(text = rationale)
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            onRationaleReply(true)
                        }
                    ) {
                        Text(text = confirmButtonText)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            onRationaleReply(false)
                        }
                    ) {
                        Text(text = dismissButtonText)
                    }
                }
            )
        }
    }
}