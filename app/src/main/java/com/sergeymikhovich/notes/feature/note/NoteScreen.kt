package com.sergeymikhovich.notes.feature.note

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.sergeymikhovich.notes.feature.note.navigation.NoteDirection

fun NavGraphBuilder.composeToNote() = composableTo(NoteDirection) { NoteScreen() }

@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    BackHandler(onBack = viewModel::saveNote)

    NoteContent(
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

@Composable
private fun NoteContent(
    title: String,
    description: String,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = Color(0xFFF8EEE2))
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                fontSize = TextUnit(24f, TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                lineHeight = TextUnit(1.2F, TextUnitType.Em),
                color = Color(0xFF595550)
            ),
            singleLine = false,
            value = title,
            onValueChange = onTitleChanged,
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (title.isEmpty()) {
                        Text(
                            modifier = Modifier.fillMaxSize(),
                            text = "Title",
                            fontSize = TextUnit(32f, TextUnitType.Sp),
                            fontWeight = FontWeight.Bold,
                            lineHeight = TextUnit(1.2F, TextUnitType.Em),
                            color = Color(0x51403B36)
                        )
                    }
                }
                innerTextField()
            }
        )
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            textStyle = TextStyle(
                fontSize = TextUnit(16f, TextUnitType.Sp),
                fontWeight = FontWeight.Medium,
                lineHeight = TextUnit(1.3F, TextUnitType.Em),
                color = Color(0xFF595550)
            ),
            singleLine = false,
            value = description,
            onValueChange = onDescriptionChanged,
            decorationBox = { innerTextField ->
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    if (description.isEmpty()) {
                        Text(
                            text = "Description",
                            fontSize = TextUnit(18f, TextUnitType.Sp),
                            fontWeight = FontWeight.Bold,
                            lineHeight = TextUnit(1.4F, TextUnitType.Em),
                            color = Color(0x51595550)
                        )
                    }
                }
                innerTextField()
            }
        )
    }
}

@Preview
@Composable
fun NotePreview() {
    NoteContent(
        title = "My first title",
        description = "To create this note I needed to mark item as failed to rewrite it",
        onTitleChanged = {},
        onDescriptionChanged = {}
    )
}