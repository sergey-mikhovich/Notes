package com.sergeymikhovich.notes.feature.note

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.core.common.navigation.composableTo
import com.sergeymikhovich.notes.core.design_system.component.BottomSheetItem
import com.sergeymikhovich.notes.core.design_system.component.NoteBasicTextField
import com.sergeymikhovich.notes.core.design_system.component.NoteDatePickerDialog
import com.sergeymikhovich.notes.core.design_system.component.NoteReminderDialog
import com.sergeymikhovich.notes.core.design_system.component.NoteTimePickerDialog
import com.sergeymikhovich.notes.core.design_system.component.RegularBottomSheet
import com.sergeymikhovich.notes.feature.note.navigation.NoteDirection
import java.util.Calendar
import java.util.Date

fun NavGraphBuilder.composeToNote() = composableTo(NoteDirection) { NoteScreen() }

@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {
    val screenState by viewModel.state.collectAsStateWithLifecycle()

    when (val noteState = screenState.noteState) {
        is NoteState.Error -> {
        }
        is NoteState.Content -> {
            NoteContent(
                noteState = noteState,
                onTitleChanged = viewModel::changeTitle,
                onDescriptionChanged = viewModel::changeDescription,
                onScheduleNote = viewModel::scheduleNote,
                onBackClick = viewModel::saveNote
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteContent(
    noteState: NoteState.Content,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onScheduleNote: (Date) -> Unit,
    onBackClick: () -> Unit
) {
    var showReminderDialog by remember { mutableStateOf(false) }
    val calendarState by remember(showReminderDialog) {
        mutableStateOf(Calendar.getInstance().apply { add(Calendar.HOUR_OF_DAY, 3) })
    }
    var showReminderBottomSheet by remember { mutableStateOf(false) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }

    if (showTimePickerDialog) {
        NoteTimePickerDialog(
            timePickerState = rememberTimePickerState(
                initialHour = calendarState.get(Calendar.HOUR_OF_DAY),
                initialMinute = calendarState.get(Calendar.MINUTE)
            ),
            onCancel = { showTimePickerDialog = false },
            onConfirm = { timePickerState ->
                calendarState.apply {
                    set(Calendar.HOUR_OF_DAY, timePickerState.hour)
                    set(Calendar.MINUTE, timePickerState.minute)
                    set(Calendar.SECOND, 0)
                }
                showTimePickerDialog = false
            }
        )
    }

    if (showDatePickerDialog) {
        NoteDatePickerDialog(
            datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = calendarState.timeInMillis
            ),
            onDismissButton = { showDatePickerDialog = false },
            onConfirmButton = { datePickerState ->
                datePickerState.selectedDateMillis?.let {
                    val calendar = Calendar.getInstance().apply { timeInMillis = it }

                    calendarState.set(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                }
                showDatePickerDialog = false
            }
        )
    }

    if (showReminderDialog) {
        NoteReminderDialog(
            calendar = calendarState,
            onDateClick = { showDatePickerDialog = true },
            onTimeClick = { showTimePickerDialog = true },
            onRepeatMode = {},
            onCancel = { showReminderDialog = false },
            onConfirm = {
                onScheduleNote(it.time)
                showReminderDialog = false
            }
        )
    }

    if (showReminderBottomSheet) {
        RegularBottomSheet(
            items = listOf(
                BottomSheetItem(
                    leadingIcon = Icons.Outlined.Notifications,
                    startText = "Later today",
                    endText = "18:00",
                    onClick = { showReminderBottomSheet = false }
                ),
                BottomSheetItem(
                    leadingIcon = Icons.Outlined.Notifications,
                    startText = "Tomorrow morning",
                    endText = "08:00",
                    onClick = { showReminderBottomSheet = false }
                ),
                BottomSheetItem(
                    leadingIcon = Icons.Outlined.Notifications,
                    startText = "Thursday morning",
                    endText = "Thu 08:00",
                    onClick = { showReminderBottomSheet = false }
                ),
                BottomSheetItem(
                    leadingIcon = Icons.Outlined.Notifications,
                    startText = "Pick a date & time",
                    onClick = {
                        showReminderBottomSheet = false
                        showReminderDialog = true
                    }
                )
            ),
            onDismissRequest = { showReminderBottomSheet = false }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                windowInsets = WindowInsets.systemBars,
                backgroundColor = Color(0xFFFFFDF0),
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showReminderBottomSheet = true }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = ""
                        )
                    }
                    IconButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = ""
                        )
                    }
                },
                elevation = 0.dp,
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(color = Color(0xFFFFFDF0))
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp
                )
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            NoteBasicTextField(
                textStyle = TextStyle(
                    fontWeight = FontWeight.Medium,
                    lineHeight = TextUnit(1.4F, TextUnitType.Em),
                    fontSize = TextUnit(24F, TextUnitType.Sp),
                    color = Color(0xFF595550)
                ),
                placeholderText = "Title",
                placeHolderStyle = TextStyle(
                    fontWeight = FontWeight.Medium,
                    lineHeight = TextUnit(1.4F, TextUnitType.Em),
                    fontSize = TextUnit(24F, TextUnitType.Sp),
                    color = Color(0x51403B36)
                ),
                value = noteState.title,
                onValueChange = onTitleChanged
            )

            NoteBasicTextField(
                modifier = Modifier.padding(top = 16.dp),
                textStyle = TextStyle(
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                    color = Color(0xFF595550),
                    fontWeight = FontWeight.Normal,
                    lineHeight = TextUnit(1.3F, TextUnitType.Em)
                ),
                placeholderText = "Description",
                placeHolderStyle = TextStyle(
                    fontSize = TextUnit(16f, TextUnitType.Sp),
                    fontWeight = FontWeight.Normal,
                    lineHeight = TextUnit(1.3F, TextUnitType.Em),
                    color = Color(0x51595550)
                ),
                value = noteState.description,
                onValueChange = onDescriptionChanged
            )
        }
    }

    BackHandler(onBack = onBackClick)
}

@Preview
@Composable
fun NotePreview() {
    NoteContent(
        noteState = NoteState.Content(
            title = "My first title",
            description = "To create this note I needed to mark item as failed to rewrite it"
        ),
        onTitleChanged = {},
        onDescriptionChanged = {},
        onScheduleNote = {},
        onBackClick = {}
    )
}