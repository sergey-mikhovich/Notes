package com.sergeymikhovich.notes.feature.note

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.app.ui.theme.NotesTheme
import com.sergeymikhovich.notes.core.common.ui.composableTo
import com.sergeymikhovich.notes.feature.note.navigation.NoteDirection

fun NavGraphBuilder.composeToNote() = composableTo(NoteDirection) { NoteScreen() }

@Composable
fun NoteScreen(viewModel: NoteViewModel = hiltViewModel()) {
    NotesTheme {
        val state by viewModel.state.collectAsStateWithLifecycle()
        val context = LocalContext.current

        BackHandler {
            viewModel.saveNote()
        }
        
        Note(
            title = state.title,
            description = state.description,
            onTitleChanged = viewModel::changeTitle,
            onDescriptionChanged = viewModel::changeDescription
        )

        LaunchedEffect(viewModel) {
            viewModel.error.collect { error ->
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@Composable
fun Note(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit
) {
    Surface(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
        ) {
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textStyle = TextStyle(
                    fontSize = TextUnit(16f, TextUnitType.Sp)
                ),
                singleLine = false,
                value = title,
                onValueChange = onTitleChanged,
                decorationBox = { innerTextField ->
                    Box(modifier = modifier) {
                        if (title.isEmpty()) {
                            Text(text = "Title")
                        }
                    }
                    innerTextField()
                }
            )
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = false,
                value = description,
                onValueChange = onDescriptionChanged,
                decorationBox = { innerTextField ->
                    Box(modifier = modifier) {
                        if (description.isEmpty()) {
                            Text(text = "Note")
                        }
                    }
                    innerTextField()
                }
            )
        }
    }
}

@Preview
@Composable
fun NotePreview() {
    NotesTheme {
        Note(
            title = "Meeting",
            description = "I want to go to meeting today",
            onTitleChanged = {},
            onDescriptionChanged = {}
        )
    }
}