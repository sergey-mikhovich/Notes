package com.sergeymikhovich.notes.core.design_system.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

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
            modifier = modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            containerColor = Color(0xFFFFFDF0),
            onDismissRequest = {
                onReply(false)
            },
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF403B36),
                    fontSize = TextUnit(22f, TextUnitType.Sp)
                )
            },
            text = {
                Text(
                    text = description,
                    color = Color(0xFF595550),
                    fontSize = TextUnit(16f, TextUnitType.Sp)
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onReply(true)
                    }
                ) {
                    Text(
                        text = confirmButtonText,
                        color = Color(0xFFD9614C),
                        fontSize = TextUnit(16f, TextUnitType.Sp)
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onReply(false)
                    }
                ) {
                    Text(
                        text = dismissButtonText,
                        color = Color(0xFF595550),
                        fontSize = TextUnit(16f, TextUnitType.Sp)
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun AlertDialogWithStatePreview() {
    AlertDialogWithState(
        state = AlertDialogState(
            title = "Confirm dialog",
            description = "Are tou sure you want to confirm that?",
            confirmButtonText = "Confirm",
            dismissButtonText = "Dismiss"
        ),
        onReply = {}
    )
}