package com.sergeymikhovich.notes.feature.notes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.app.ui.theme.NotesTheme
import com.sergeymikhovich.notes.core.common.ui.composableTo
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.feature.notes.navigation.NotesDirection

fun NavGraphBuilder.composableToNotes() = composableTo(NotesDirection) { NotesScreen() }

@Composable
fun NotesScreen(viewModel: NotesViewModel = hiltViewModel()) {
    NotesTheme {
        val state by viewModel.state.collectAsStateWithLifecycle()

        if (state.isEmpty) {
            EmptyNotes(
                onButtonClick = viewModel::onCreateNoteClick
            )
        } else {
            Box(modifier = Modifier.fillMaxSize()) {
                Notes(
                    notes = state.notes,
                    onNoteClick = viewModel::onOpenNoteClick,
                    onNoteLongClick = viewModel::onDeleteNoteClick
                )
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        modifier = Modifier.padding(16.dp),
                        onClick =  viewModel::onCreateNoteClick
                    ) {
                        Text(text = "Add note")
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyNotes(onButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = onButtonClick) {
            Text(text = "Tap to create new note")
        }
    }
}

@Composable
fun Notes(
    notes: List<Note>,
    onNoteClick: (noteId: String) -> Unit,
    onNoteLongClick: (noteId: String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(items = notes, key = { it.id }) { note ->
            NoteCard(
                title = note.title,
                description = note.description,
                onNoteClick = { onNoteClick(note.id) },
                onNoteLongClick = { onNoteLongClick(note.id) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onNoteClick: () -> Unit,
    onNoteLongClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .combinedClickable(
                onClick = onNoteClick,
                onLongClick = onNoteLongClick
            ),
        border = BorderStroke(
            width = 1.dp,
            color = Color.Gray
        ),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                text = title,
                fontSize = TextUnit(20f, TextUnitType.Sp),
                fontWeight = FontWeight.Medium
            )
            Text(text = description)
        }
    }
}

@Preview
@Composable
fun NotesPreview() {
    NotesTheme {
        Surface {
            val list = listOf(
                Note("Task", "Description"),
                Note("Task1", "Description1"),
                Note("Task3", "Description3")
            )
            EmptyNotes {

            }
            //Notes(list, {})
        }
    }
}