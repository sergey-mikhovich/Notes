package com.sergeymikhovich.notes.feature.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeymikhovich.notes.core.data.SyncManager
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.core.data.repository.NoteRepository
import com.sergeymikhovich.notes.feature.notes.navigation.NotesRouter
import com.sergeymikhovich.notes.sync.workers.SyncWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
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
    private val noteRepository: NoteRepository,
    private val router: NotesRouter
) : ViewModel(), NotesRouter by router {

    val state: StateFlow<NotesState> = noteRepository.observeAll()
        .map { NotesState(notes = it, isEmpty = it.isEmpty()) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), NotesState(isLoading = true))

    fun onOpenNoteClick(noteId: String) {
        router.toNote(noteId)
    }

    fun onCreateNoteClick() {
        router.toCreateNote()
    }

    fun onDeleteNoteClick(noteId: String) {
        viewModelScope.launch {
            noteRepository.deleteById(noteId)
        }
    }
}