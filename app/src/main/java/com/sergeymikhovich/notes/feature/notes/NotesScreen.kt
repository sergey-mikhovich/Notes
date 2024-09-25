package com.sergeymikhovich.notes.feature.notes

import android.Manifest
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.R
import com.sergeymikhovich.notes.core.common.navigation.composableTo
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.core.permission.PermissionScreen
import com.sergeymikhovich.notes.core.permission.PermissionScreenState
import com.sergeymikhovich.notes.core.permission.goToAppSettings
import com.sergeymikhovich.notes.core.permission.rememberMultiplePermissionsState
import com.sergeymikhovich.notes.feature.notes.navigation.NotesDirection

fun NavGraphBuilder.composableToNotes() = composableTo(NotesDirection) { NotesScreen() }

@Composable
fun NotesScreen(
    viewModel: NotesViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val deniedPermissionsState by viewModel.deniedPermissionsState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.POST_NOTIFICATIONS,
            Manifest.permission.RECORD_AUDIO
        ),
        onPermissionsResult = { resultMap ->
            viewModel.markAsPermanentlyDenied(
                resultMap
                    .filter { !it.value }
                    .map { it.key }
                    .toSet()
            )
        }
    )

    val permissionScreenState by remember {
        derivedStateOf {
            PermissionScreenState(
                title = "Permission denied",
                rationale = "This permission needed for correct app work",
                confirmButtonText = if (permissionsState.shouldShowRationale) "Confirm" else "Go to Settings",
                dismissButtonText = "Dismiss"
            )
        }
    }

    val showRationaleDialog by remember {
        derivedStateOf {
            !deniedPermissionsState.containsAll(permissionsState.revokedPermissions.map { it.permission }.toSet())
        }
    }

    if (showRationaleDialog) {
        PermissionScreen(
            state = permissionScreenState,
            onRationaleReply = { accepted ->
                if (accepted) {
                    if (permissionsState.shouldShowRationale)
                        permissionsState.launchMultiplePermissionsRequest()
                    else {
                        viewModel.markAsPermanentlyDenied(permissionsState.revokedPermissions.map { it.permission }.toSet())
                        context.goToAppSettings()
                    }
                } else {
                    viewModel.markAsPermanentlyDenied(permissionsState.revokedPermissions.map { it.permission }.toSet())
                }
            }
        )
    }

    NotesContent(
        state = state,
        onCreateNoteClick = viewModel::toCreateNote,
        onAccountCenterClick = viewModel::toAccountCenter,
        onOpenNoteClick = viewModel::toNote,
        onDeleteNoteClick = viewModel::onDeleteNoteClick
    )
}

@Composable
private fun NotesContent(
    state: NotesState,
    onCreateNoteClick: () -> Unit,
    onAccountCenterClick: () -> Unit,
    onOpenNoteClick: (String) -> Unit,
    onDeleteNoteClick: (String) -> Unit
) {
    Scaffold(
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
                .background(color = Color(0xFFF8EEE2))
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            TopAppBar(
                backgroundColor = Color(0xFFF8EEE2),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = TextUnit(24f, TextUnitType.Sp),
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF403B36)
                    )
                },
                actions = {
                    IconButton(onClick = onAccountCenterClick) {
                        Icon(
                            imageVector = Icons.Filled.Person,
                            contentDescription = "Account center",
                            tint = Color(0xFF595550)
                        )
                    }
                },
                elevation = 0.dp
            )

            Spacer(modifier = Modifier.height(8.dp))

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
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(16.dp, 0.dp, 16.dp, 8.dp)
            .border(
                width = 0.dp,
                color = Color(0xFFFFFDF0),
                shape = RoundedCornerShape(12.dp)
            )
            .combinedClickable(
                onClick = onOpenNoteClick,
                onLongClick = onDeleteNoteClick
            ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    color = Color(0xFFFFFDF0),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(bottom = 16.dp),
                text = title,
                fontSize = TextUnit(18f, TextUnitType.Sp),
                fontWeight = FontWeight.ExtraBold,
                color = Color(0xFF595550)
            )
            Text(
                text = description,
                fontSize = TextUnit(14f, TextUnitType.Sp),
                fontWeight = FontWeight.Medium,
                lineHeight = TextUnit(1.3f, TextUnitType.Em),
                color = Color(0xFF595550)
            )
        }
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
        onCreateNoteClick = {},
        onAccountCenterClick = {},
        onOpenNoteClick = {},
        onDeleteNoteClick = {}
    )
}