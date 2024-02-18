package com.sergeymikhovich.notes.feature.presentation.notes

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergeymikhovich.notes.feature.domain.note.api.GetNotesUseCase
import com.sergeymikhovich.notes.feature.domain.note.api.model.Note
import com.sergeymikhovich.notes.feature.presentation.notes.navigation.NotesRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEmpty
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

data class NotesState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val userMessage: Int? = null
)

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase,
    private val router: NotesRouter
) : ViewModel(), NotesRouter by router {

    private val _state: MutableStateFlow<NotesState> = MutableStateFlow(NotesState())
    val state: StateFlow<NotesState> = flow { emit(getNotesUseCase()) }
        .transform {
            if (it.isNotEmpty()) {
                emit(NotesState(notes = it))
            }
        }
        .onEmpty { emit(NotesState(isEmpty = true)) }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), NotesState(isLoading = true))

    fun onNoteClick(noteId: Long) {
        router.toNote(noteId)
    }

    fun onCreateNoteClick() {
        Log.d("NotesPass", "onCreateNoteClick")
        router.toCreateNote()
    }
}