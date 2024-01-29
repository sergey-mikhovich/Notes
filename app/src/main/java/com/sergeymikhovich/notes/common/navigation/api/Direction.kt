package com.sergeymikhovich.notes.common.navigation.api

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavOptionsBuilder

abstract class Direction(protected val target: String) {

    open val route: String = target
    open val arguments: List<NamedNavArgument> get() = emptyList()
    open val deepLinks: List<NavDeepLink> get() = emptyList()

    protected fun createNavAction(
        destination: String,
        builder: (NavOptionsBuilder.() -> Unit)? = null
    ): NavigationAction {
        return NavigationAction(destination, builder)
    }
}