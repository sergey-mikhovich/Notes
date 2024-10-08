package com.sergeymikhovich.notes.feature.note.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.sergeymikhovich.notes.core.common.navigation.Direction
import com.sergeymikhovich.notes.core.common.navigation.NavigationAction

private const val KEY_NOTE_ID = "noteId"

object NoteDirection : Direction("note") {

    override val route: String
        get() = "$target?$KEY_NOTE_ID={$KEY_NOTE_ID}"

    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument(KEY_NOTE_ID) { type = NavType.StringType })

    fun createActionWithId(noteId: String = ""): NavigationAction = createNavAction("$target?$KEY_NOTE_ID=$noteId")

    fun getNoteId(savedStateHandle: SavedStateHandle): String? = savedStateHandle.get<String>(KEY_NOTE_ID)
}