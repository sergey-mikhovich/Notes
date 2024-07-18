package com.sergeymikhovich.notes.feature.notes.navigation

import com.sergeymikhovich.notes.core.common.navigation.Direction
import com.sergeymikhovich.notes.core.common.navigation.NavigationAction

object NotesDirection : Direction("notes") {

    fun createAction(): NavigationAction = createNavAction(route)
}