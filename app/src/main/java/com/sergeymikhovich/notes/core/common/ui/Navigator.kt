package com.sergeymikhovich.notes.core.common.ui

import kotlinx.coroutines.flow.SharedFlow

interface Navigator {

    val navActions: SharedFlow<Direction>

    fun navigateTo(navAction: NavigationAction)

    fun navigateUp()

    sealed interface Direction {
        data class Forward(val navAction: NavigationAction) : Direction
        data object Back : Direction
    }
}