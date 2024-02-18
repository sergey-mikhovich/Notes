package com.sergeymikhovich.notes.feature.presentation.notes.navigation

import com.sergeymikhovich.notes.common.navigation.api.Direction
import com.sergeymikhovich.notes.common.navigation.api.NavigationAction

object NotesDirection : Direction("notes") {

    fun createAction(): NavigationAction = createNavAction(route)
}