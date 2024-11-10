package com.sergeymikhovich.notes.core.design_system.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Create
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.isContainer
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.zIndex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteDatePickerDialog(
    datePickerState: DatePickerState,
    onConfirmButton: (DatePickerState) -> Unit,
    onDismissButton: () -> Unit
) {
    val confirmEnabled = remember {
        derivedStateOf { datePickerState.selectedDateMillis != null }
    }
    DatePickerDialog(
        modifier = Modifier.scale(0.9f),
        onDismissRequest = onDismissButton,
        confirmButton = {
            TextButton(
                onClick = { onConfirmButton(datePickerState) },
                enabled = confirmEnabled.value
            ) {
                Text(modifier = Modifier.scale(1.1f), text = "OK")
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismissButton
            ) {
                Text(modifier = Modifier.scale(1.1f), text = "Cancel")
            }
        }
    ) {
        DatePicker(
            state = datePickerState,
            modifier = Modifier.verticalScroll(rememberScrollState())
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteTimePickerDialog(
    timePickerState: TimePickerState,
    onCancel: () -> Unit,
    onConfirm: (TimePickerState) -> Unit
) {
    var showingPicker by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current

    TimePickerDialog(
        title = if (showingPicker) "Select Time" else "Enter Time",
        onCancel = onCancel,
        onConfirm = { onConfirm(timePickerState) },
        toggle = {
            if (configuration.screenHeightDp > 400) {
                Box(
                    Modifier.fillMaxSize().semantics{ isContainer = true }
                ) {
                    IconButton(
                        modifier = Modifier
                            .size(64.dp, 72.dp)
                            .align(Alignment.BottomStart)
                            .zIndex(5f),
                        onClick = { showingPicker = !showingPicker }
                    ) {
                        val icon = if (showingPicker) {
                            Icons.Outlined.Create
                        } else {
                            Icons.Outlined.DateRange
                        }
                        Icon(
                            icon,
                            contentDescription = if (showingPicker) {
                                "Switch to Text Input"
                            } else {
                                "Switch to Touch Input"
                            }
                        )
                    }
                }
            }
        }
    ) {
        if (showingPicker && configuration.screenHeightDp > 400) {
            TimePicker(state = timePickerState)
        } else {
            TimeInput(state = timePickerState)
        }
    }
}

@Composable
fun TimePickerDialog(
    title: String = "Enter Time",
    onCancel: () -> Unit,
    onConfirm: () -> Unit,
    toggle: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
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
                .width(IntrinsicSize.Min)
                .height(IntrinsicSize.Min)
                .background(
                    shape = MaterialTheme.shapes.extraLarge,
                    color = MaterialTheme.colorScheme.surface
                ),
        ) {
            toggle()
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp),
                    text = title,
                    style = MaterialTheme.typography.labelMedium
                )
                content()
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
                        onClick = onConfirm
                    ) { Text("OK") }
                }
            }
        }
    }
}

