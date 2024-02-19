package com.sergeymikhovich.notes.feature.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeymikhovich.notes.feature.domain.note.api.DeleteNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.GetNoteUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.GetNotesUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.ObserveNotesUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.presentation.notes.navigation.NotesRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotesState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val userMessage: Int? = null
)

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val observeNotesUseCase: ObserveNotesUseCase,
    private val deleteNoteUseCase: DeleteNoteUseCase,
    private val router: NotesRouter
) : ViewModel(), NotesRouter by router {

    val state: StateFlow<NotesState> = observeNotesUseCase()
        .map { NotesState(notes = it, isEmpty = it.isEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), NotesState(isLoading = true))

    fun onOpenNoteClick(noteId: Long) {
        router.toNote(noteId)
    }

    fun onCreateNoteClick() {
        router.toCreateNote()
    }

    fun onDeleteNoteClick(noteId: Long) {
        viewModelScope.launch {
            deleteNoteUseCase(noteId)
        }
    }
}