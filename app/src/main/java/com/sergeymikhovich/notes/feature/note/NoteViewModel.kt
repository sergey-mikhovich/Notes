package com.sergeymikhovich.notes.feature.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.fold
import com.github.michaelbull.result.onSuccess
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sergeymikhovich.notes.core.data.repository.NoteRepository
import com.sergeymikhovich.notes.core.domain.UpsertNoteUseCase
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.core.network.getCurrentUserId
import com.sergeymikhovich.notes.feature.note.navigation.NoteDirection
import com.sergeymikhovich.notes.feature.note.navigation.NoteRouter
import com.sergeymikhovich.notes.reminder.ReminderItem
import com.sergeymikhovich.notes.reminder.ReminderScheduler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

data class NoteScreenState(
    val noteState: NoteState
)

sealed interface NoteState {
    data object Error: NoteState
    data class Content(
        val title: String = "",
        val description: String = ""
    ): NoteState
}

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val upsertNoteUseCase: UpsertNoteUseCase,
    private val router: NoteRouter,
    private val reminderScheduler: ReminderScheduler,
    private val savedStateHandle: SavedStateHandle
): ViewModel(), NoteRouter by router, ReminderScheduler by reminderScheduler {

    private val _state: MutableStateFlow<NoteScreenState> = MutableStateFlow(
        NoteScreenState(NoteState.Content())
    )
    val state: MutableStateFlow<NoteScreenState> = _state

    init {
        viewModelScope.launch {
            val noteId = getNoteId() ?: ""
            noteRepository.getById(noteId).fold(
                success = { note ->
                    _state.update {
                        it.copy(noteState = NoteState.Content(
                            note?.title ?: "",
                            note?.description ?: ""
                        ))
                    }
                },
                failure = { _state.update { it.copy(noteState = NoteState.Error) } }
            )
        }
    }

    fun scheduleNote(date: Date) {
        viewModelScope.launch {
            val noteId = getNoteId() ?: ""
            noteRepository.getById(noteId).onSuccess { note ->
                note?.let {
                    schedule(ReminderItem(date, note))
                }
            }
        }
    }

    private fun getNoteId(): String? {
        return NoteDirection.getNoteId(savedStateHandle)
    }

    fun changeTitle(title: String) {
        _state.update {
            it.copy(noteState = NoteState.Content(title = title))
        }
    }

    fun changeDescription(description: String) {
        _state.update {
            it.copy(noteState = NoteState.Content(description = description))
        }
    }

    fun saveNote() {
        onSaveContentState {
            viewModelScope.launch(Dispatchers.IO) {
                val noteId = getNoteId() ?: ""

                val note = if (noteId.isNotBlank()) {
                    Note(
                        id = noteId,
                        userId = Firebase.auth.getCurrentUserId(),
                        title = title,
                        description = description
                    )
                } else {
                    Note(
                        userId = Firebase.auth.getCurrentUserId(),
                        title = title,
                        description = description
                    )
                }

                upsertNoteUseCase(note).fold(
                    success = {},
                    failure = {}
                )

                back()
            }
        }
    }

    private inline fun onSaveContentState(block: NoteState.Content.() -> Unit) =
        (_state.value.noteState as? NoteState.Content)?.let(block)
}