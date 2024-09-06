package com.sergeymikhovich.notes.core.common.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.NavOptions
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

    fun createAction(): NavigationAction {
        return NavigationAction(route, null)
    }

    fun createActionAndPopUpTo(popUpToRoute: String): NavigationAction {
        return createNavAction(route) {
            popUpTo(popUpToRoute) {
                inclusive = true
            }
        }
    }
}