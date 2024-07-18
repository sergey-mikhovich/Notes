package com.sergeymikhovich.notes.feature.notes.navigation

import com.sergeymikhovich.notes.core.common.ui.Direction
import com.sergeymikhovich.notes.core.common.ui.NavigationAction

object NotesDirection : Direction("notes") {

    fun createAction(): NavigationAction = createNavAction(route)
}