package com.sergeymikhovich.notes.feature.presentation.note.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.sergeymikhovich.notes.common.navigation.api.Direction
import com.sergeymikhovich.notes.common.navigation.api.NavigationAction

private const val KEY_NOTE_ID = "noteId"

object NoteDirection : Direction("note") {

    override val route: String
        get() = "$target/{$KEY_NOTE_ID}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument(KEY_NOTE_ID) { type = NavType.LongType })

    fun createAction(noteId: Long): NavigationAction = createNavAction("$target/${noteId}")

    fun getNoteId(savedStateHandle: SavedStateHandle): Long? = savedStateHandle.get<Long>(KEY_NOTE_ID)
}