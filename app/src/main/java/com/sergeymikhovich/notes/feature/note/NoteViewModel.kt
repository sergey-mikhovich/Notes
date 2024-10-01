package com.sergeymikhovich.notes.feature.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sergeymikhovich.notes.core.data.repository.NoteRepository
import com.sergeymikhovich.notes.core.domain.UpsertNoteResult
import com.sergeymikhovich.notes.core.domain.UpsertNoteUseCase
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.core.network.getCurrentUserId
import com.sergeymikhovich.notes.feature.note.navigation.NoteDirection
import com.sergeymikhovich.notes.feature.note.navigation.NoteRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NoteState(
    val title: String = "",
    val description: String = ""
)

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val upsertNoteUseCase: UpsertNoteUseCase,
    private val router: NoteRouter,
    private val savedStateHandle: SavedStateHandle
): ViewModel(), NoteRouter by router {

    private val _state: MutableStateFlow<NoteState> = MutableStateFlow(NoteState())
    val state: MutableStateFlow<NoteState> = _state

    init {
        viewModelScope.launch {
            val noteId = getNoteId() ?: ""
            noteRepository.getById(noteId)?.let { note ->
                _state.update { it.copy(title = note.title, description = note.description) }
            }
        }
    }

    private fun getNoteId(): String? {
        return NoteDirection.getNoteId(savedStateHandle)
    }

    fun changeTitle(title: String) {
        _state.update { it.copy(title = title) }
    }

    fun changeDescription(description: String) {
        _state.update { it.copy(description = description) }
    }

    fun saveNote() {
        viewModelScope.launch {
            val note = _state.value
            val noteId = getNoteId() ?: ""

            if (noteId.isNotBlank()) {
                upsertNoteUseCase(
                    Note(
                        id = noteId,
                        userId = Firebase.auth.getCurrentUserId(),
                        title = note.title,
                        description = note.description
                    )
                )
                back()
            } else {
                when (upsertNoteUseCase(
                    Note(
                        userId = Firebase.auth.getCurrentUserId(),
                        title = note.title,
                        description = note.description))
                ) {
                    UpsertNoteResult.Success -> back()
                    UpsertNoteResult.EmptyNote -> back()
                    UpsertNoteResult.Fail -> back()
                }
            }
        }
    }
}