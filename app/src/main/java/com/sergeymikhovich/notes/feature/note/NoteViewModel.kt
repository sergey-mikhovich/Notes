package com.sergeymikhovich.notes.feature.note

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.sergeymikhovich.notes.core.data.repository.NoteRepository
import com.sergeymikhovich.notes.core.domain.UpsertNoteUseCase
import com.sergeymikhovich.notes.core.domain.UpsertResult
import com.sergeymikhovich.notes.core.model.Note
import com.sergeymikhovich.notes.core.network.getCurrentUserId
import com.sergeymikhovich.notes.feature.note.navigation.NoteDirection
import com.sergeymikhovich.notes.feature.note.navigation.NoteRouter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Data(
    val title: String = "",
    val description: String = ""
)

data class NoteState(
    val data: Data = Data(),
    val error: String = "",
    val isLoading: Boolean = false
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

    private val data: Data
        get() = _state.value.data

    init {
        viewModelScope.launch {
            val noteId = getNoteId() ?: ""
            noteRepository.getById(noteId)
                .onSuccess { note ->
                    if (note != null) {
                        _state.update {
                            NoteState(
                                data = data.copy(
                                    title = note.title,
                                    description = note.description)
                            )
                        }
                    } else {
                        showError(defaultValue = "Ooops...Something went wrong")
                    }
                }
                .onFailure { showError(it.message, "Ooops...Something went wrong") }
        }
    }

    private fun getNoteId(): String? {
        return NoteDirection.getNoteId(savedStateHandle)
    }

    fun changeTitle(title: String) {
        _state.update { NoteState(data = data.copy(title = title)) }
    }

    fun changeDescription(description: String) {
        _state.update { NoteState(data = data.copy(description = description)) }
    }

    private fun showError(errorMessage: String? = null, defaultValue: String = "") {
        _state.update {
            NoteState(data = data, error = errorMessage ?: defaultValue)
        }
    }

    fun saveNote() {
        viewModelScope.launch {
            val noteId = getNoteId() ?: ""

            upsertNoteUseCase(
                if (noteId.isNotBlank()) {
                    Note(
                        id = noteId,
                        userId = Firebase.auth.getCurrentUserId(),
                        title = data.title,
                        description = data.description
                    )
                } else {
                    Note(
                        userId = Firebase.auth.getCurrentUserId(),
                        title = data.title,
                        description = data.description
                    )
                }
            )
                .onSuccess { upsertResult ->
                    when(upsertResult) {
                        is UpsertResult.Success -> {}
                        is UpsertResult.EmptyNote -> {}
                        is UpsertResult.Error -> {}
                    }
                }
                .onFailure { showError(it.message, "Ooops...Something went wrong") }

            back()
        }
    }
}