package com.sergeymikhovich.notes.feature.notes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import coil.compose.AsyncImage
import com.sergeymikhovich.notes.R
import com.sergeymikhovich.notes.core.common.navigation.composableTo
import com.sergeymikhovich.notes.core.design_system.component.AlertDialogState
import com.sergeymikhovich.notes.core.design_system.component.AlertDialogWithState
import com.sergeymikhovich.notes.core.design_system.component.NotesSearchBar
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.core.model.User
import com.sergeymikhovich.notes.feature.notes.navigation.NotesDirection

fun NavGraphBuilder.composableToNotes() = composableTo(NotesDirection) { NotesScreen() }

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val user by viewModel.user.collectAsStateWithLifecycle()

    NotesContent(
        state = state,
        user = user,
        onCreateNoteClick = viewModel::toCreateNote,
        onAccountCenterClick = viewModel::toAccountCenter,
        onOpenNoteClick = viewModel::toNote,
        onDeleteNoteClick = viewModel::onDeleteNoteClick
    )
}

@Composable
private fun NotesContent(
    state: NotesState,
    user: User,
    onCreateNoteClick: () -> Unit,
    onAccountCenterClick: () -> Unit,
    onOpenNoteClick: (String) -> Unit,
    onDeleteNoteClick: (String) -> Unit
) {
    Scaffold(
        topBar = {
            var active by remember { mutableStateOf(false) }

            Column{
                LinearProgressIndicator(
                    modifier = Modifier.fillMaxWidth().background(Color(0xFFFFFDF0)),
                    color = if (state.isLoading) Color(0xFFD9614C) else Color(0xFFFFFDF0)
                )
                NotesSearchBar(
                    active = active,
                    onActiveChange = { active = it },
                    onSearch = {},
                    placeholder = {
                        Text(
                            text = "Search your notes",
                            fontSize = TextUnit(16f, TextUnitType.Sp),
                            fontWeight = FontWeight.Normal
                        )
                    },
                    leadingIcon = {
                        if (!active) {
                            IconButton(onClick = {}) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = ""
                                )
                            }
                        } else {
                            IconButton(
                                onClick = { active = false }
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = ""
                                )
                            }
                        }
                    },
                    trailingIcon = {
                        if (!active) {
                            var iconSize by remember { mutableStateOf(24.dp) }
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .clickable(onClick = onAccountCenterClick),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = user.photoUri,
                                    contentDescription = "",
                                    placeholder = rememberVectorPainter(image = Icons.Filled.Person),
                                    error = rememberVectorPainter(image = Icons.Filled.Person),
                                    fallback = rememberVectorPainter(image = Icons.Filled.Person),
                                    onSuccess = { iconSize = 32.dp },
                                    onLoading = { iconSize = 24.dp },
                                    onError = { iconSize = 24.dp },
                                    modifier = Modifier
                                        .size(iconSize)
                                        .clip(CircleShape)
                                )
                            }
                        }
                    }
                ) {

                }
            }
        },
        floatingActionButton = {
            if (!state.isEmpty) {
                FloatingActionButton(
                    onClick = onCreateNoteClick,
                    modifier = Modifier.padding(16.dp),
                    backgroundColor = Color(0xFFD9614C),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add",
                        tint = Color.White
                    )
                }
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color(0xFFFFFDF0))
                .padding(paddingValues)
        ) {
            if (state.isEmpty) {
                EmptyNotes(onCreateNoteClick = onCreateNoteClick)
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Notes(
                        notes = state.notes,
                        onOpenNoteClick = onOpenNoteClick,
                        onDeleteNoteClick = onDeleteNoteClick
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyNotes(
    onCreateNoteClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                horizontal = 60.dp
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(200.dp),
            painter = painterResource(id = R.drawable.start_your_jorney),
            contentDescription = ""
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Start Your Journey",
            fontSize = TextUnit(28f, TextUnitType.Sp),
            fontWeight = FontWeight.ExtraBold,
            color = Color(0xFF403B36),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Every big step start with small step. Notes your first idea and start your journey!",
            fontSize = TextUnit(16f, TextUnitType.Sp),
            fontWeight = FontWeight.Bold,
            color = Color(0xAF595550),
            textAlign = TextAlign.Center,
            lineHeight = TextUnit(1.4f, TextUnitType.Em)
        )

        Image(
            modifier = Modifier.size(128.dp),
            painter = painterResource(id = R.drawable.arc_shape_arrow),
            contentDescription = ""
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(12.dp),
            onClick = onCreateNoteClick,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(0xFFD9614C)
            ),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 0.dp
            )
        ) {
            Text(
                text = "Create a Note",
                lineHeight = TextUnit(1.4F, TextUnitType.Em),
                fontSize = TextUnit(16F, TextUnitType.Sp),
                color = Color.White
            )
        }
    }
}

@Composable
fun Notes(
    notes: List<Note>,
    onOpenNoteClick: (String) -> Unit,
    onDeleteNoteClick: (String) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items = notes, key = { it.id }) { note ->
            NoteCard(
                title = note.title,
                description = note.description,
                onOpenNoteClick = { onOpenNoteClick(note.id) },
                onDeleteNoteClick = { onDeleteNoteClick(note.id) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoteCard(
    title: String,
    description: String,
    onOpenNoteClick: () -> Unit,
    onDeleteNoteClick: () -> Unit
) {
    var showDeleteAlertDialog by remember { mutableStateOf(false) }

    if (showDeleteAlertDialog) {
        AlertDialogWithState(
            AlertDialogState(
                title = "Delete note",
                description = "Are you sure you want to delete note?",
                confirmButtonText = "Delete",
                dismissButtonText = "Cancel"
            ),
            onReply = { accepted ->
                if (accepted)
                    onDeleteNoteClick()

                showDeleteAlertDialog = false
            }
        )
    }
    Column(
        modifier = Modifier
            .padding(8.dp, 0.dp, 8.dp, 8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(RoundedCornerShape(12.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(12.dp))
            .combinedClickable(
                onClick = onOpenNoteClick,
                onLongClick = { showDeleteAlertDialog = true }
            )
            .background(
                color = Color(0xFFFFFDF0),
                shape = RoundedCornerShape(12.dp)
            )
            .padding(16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp),
            text = title,
            color = Color(0xFF403B36),
            fontWeight = FontWeight.Medium,
            lineHeight = TextUnit(1.4F, TextUnitType.Em),
            fontSize = TextUnit(18F, TextUnitType.Sp)
        )
        Text(
            text = description,
            lineHeight = TextUnit(1.4F, TextUnitType.Em),
            fontWeight = FontWeight.Normal,
            fontSize = TextUnit(15F, TextUnitType.Sp),
            color = Color(0xFF595550)
        )
    }
}

@Preview
@Composable
fun NotesScreenPreview() {
    NotesContent(
        state = NotesState(
            notes = listOf(
                Note(
                    userId = "userId",
                    title = "My first title",
                    description = "To speak about my drawbacks you need to know how to deal with my assistants")
            )
        ),
        user = User(),
        onCreateNoteClick = {},
        onAccountCenterClick = {},
        onOpenNoteClick = {},
        onDeleteNoteClick = {}
    )
}