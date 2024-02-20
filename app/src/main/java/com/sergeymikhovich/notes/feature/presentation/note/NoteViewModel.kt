package com.sergeymikhovich.notes.feature.presentation.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeymikhovich.notes.feature.domain.note.api.AddNoteResult
import com.sergeymikhovich.notes.feature.domain.note.api.AddNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.GetNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.UpdateNoteUseCase
import com.sergeymikhovich.notes.feature.presentation.note.navigation.NoteDirection
import com.sergeymikhovich.notes.feature.presentation.note.navigation.NoteRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
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
    private val getNoteUseCase: GetNoteUseCase,
    private val addNoteUseCase: AddNoteUseCase,
    private val updateNoteUseCase: UpdateNoteUseCase,
    private val router: NoteRouter,
    private val savedStateHandle: SavedStateHandle
): ViewModel(), NoteRouter by router {

    private val _state: MutableStateFlow<NoteState> = MutableStateFlow(NoteState())
    val state: MutableStateFlow<NoteState> = _state

    private val _error: MutableSharedFlow<String> = MutableSharedFlow(replay = 1)
    val error: MutableSharedFlow<String> = _error

    init {
        viewModelScope.launch {
            val noteId = getNoteId() ?: ""
            getNoteUseCase(noteId)?.let { note ->
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
                updateNoteUseCase(noteId, note.title, note.description)
                router.back()
            } else {
                when (addNoteUseCase(note.title, note.description)) {
                    AddNoteResult.Success -> router.back()
                    AddNoteResult.EmptyNote -> {
                        _error.tryEmit("Empty note discarded")
                        router.back()
                    }
                    AddNoteResult.FailOnAdd -> _error.tryEmit("Something went wrong while saving")
                }
            }
        }
    }
}