package com.sergeymikhovich.notes.core.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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

@Composable
fun NoteReminderDialog(
    title: String = "Add reminder",
    calendar: Calendar,
    onDateClick: () -> Unit,
    onTimeClick: () -> Unit,
    onRepeatMode: () -> Unit,
    onCancel: () -> Unit,
    onConfirm: (Calendar) -> Unit
) {
    val dateFormat = SimpleDateFormat("MMMM d", Locale.getDefault())
    val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

    Dialog(
        onDismissRequest = onCancel,
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            tonalElevation = 6.dp,
            modifier = Modifier
                .padding(32.dp)
                .wrapContentHeight()
                .wrapContentWidth()
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.headlineSmall
                )

                Divider()

                Row(
                    modifier = Modifier
                        .heightIn(min = 48.dp)
                        .fillMaxWidth()
                        .clickable(onClick = onDateClick)
                        .padding(start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(dateFormat.format(calendar.time))
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = null
                    )
                }

                Divider()


                Row(
                    modifier = Modifier
                        .heightIn(min = 48.dp)
                        .fillMaxWidth()
                        .clickable(onClick = onTimeClick)
                        .padding(start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(timeFormat.format(calendar.time))
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = null
                    )
                }

                Divider()

                Row(
                    modifier = Modifier
                        .heightIn(min = 48.dp)
                        .fillMaxWidth()
                        .clickable(onClick = onRepeatMode)
                        .padding(start = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Does not repeat")
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowDown,
                        contentDescription = null
                    )
                }

                Divider()

                Spacer(modifier = Modifier.height(20.dp))
                
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    TextButton(
                        onClick = onCancel
                    ) { Text("Cancel") }
                    TextButton(
                        onClick = { onConfirm(calendar) }
                    ) { Text("OK") }
                }
            }
        }
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

@Preview
@Composable
private fun NoteReminderDialogPreview() {
    NoteReminderDialog(
        calendar = Calendar.getInstance(),
        onCancel = {},
        onConfirm = {},
        onDateClick = {},
        onTimeClick = {},
        onRepeatMode = {}
    )
}