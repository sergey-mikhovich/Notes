package com.sergeymikhovich.notes.feature.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeymikhovich.notes.core.data.repository.AccountRepository
import com.sergeymikhovich.notes.core.data.repository.NoteRepository
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.core.model.User
import com.sergeymikhovich.notes.feature.notes.navigation.NotesRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NotesScreenState(
    val notesState: NotesState,
    val user: User
)

sealed interface NotesState {
    data object Loading: NotesState
    data object Error: NotesState
    data class Content(
        val notes: List<Note> = emptyList()
    ): NotesState
}

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteRepository: NoteRepository,
    private val accountRepository: AccountRepository,
    private val router: NotesRouter
) : ViewModel(), NotesRouter by router {

    val state: StateFlow<NotesScreenState> =
        combine(noteRepository.observeAll(), accountRepository.observeCurrentUser()) { notes, user ->
            NotesScreenState(
                user = user,
                notesState = NotesState.Content(notes)
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            NotesScreenState(user = User(), notesState = NotesState.Loading)
        )

    fun onDeleteNoteClick(noteId: String) {
        viewModelScope.launch {
            noteRepository.deleteById(noteId)
        }
    }
}