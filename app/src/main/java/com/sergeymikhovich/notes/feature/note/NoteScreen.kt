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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.sergeymikhovich.notes.core.design_system.component.NoteBasicTextField
import com.sergeymikhovich.notes.feature.note.navigation.NoteDirection

fun NavGraphBuilder.composeToNote() = composableTo(NoteDirection) { NoteScreen() }

@Composable
fun NoteScreen(
    viewModel: NoteViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    NoteContent(
        state = state,
        onTitleChanged = viewModel::changeTitle,
        onDescriptionChanged = viewModel::changeDescription,
        onBackClick = viewModel::saveNote
    )
}

@Composable
private fun NoteContent(
    state: NoteState,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onBackClick: () -> Unit
) {
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
                        onClick = { /*TODO*/ }
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
                value = state.title,
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
                value = state.description,
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
        state = NoteState(
            title = "My first title",
            description = "To create this note I needed to mark item as failed to rewrite it",
        ),
        onTitleChanged = {},
        onDescriptionChanged = {},
        onBackClick = {}
    )
}