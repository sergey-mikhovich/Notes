package com.sergeymikhovich.notes.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

data class PermissionScreenState(
    val title: String = "",
    val rationale: String = "",
    val permission: String = ""
)

@Composable
fun PermissionScreen(
    state: PermissionScreenState,
    onRationaleReply: (Boolean) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AlertDialog(
            onDismissRequest = { onRationaleReply(false) },
            title = { Text(text = state.title) },
            text = { Text(text = state.rationale) },
            confirmButton = {
                TextButton(
                    onClick = { onRationaleReply(true) }
                ) {
                    Text(text = "Open Settings")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { onRationaleReply(false) }
                ) {
                    Text(text = "Dismiss")
                }
            }
        )
    }
}

@Composable
@Preview
fun PermissionScreenPreview() {
    PermissionScreen(
        state = PermissionScreenState(
            title = "Title",
            rationale = "You need to give a permission"
        ),
        onRationaleReply = {}
    )
}