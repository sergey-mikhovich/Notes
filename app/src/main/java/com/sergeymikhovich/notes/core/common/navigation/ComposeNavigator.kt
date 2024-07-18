package com.sergeymikhovich.notes.core.common.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class ComposeNavigator @Inject constructor() : Navigator {

    private val _navActions: MutableSharedFlow<Navigator.Direction> = MutableSharedFlow(1)
    override val navActions: SharedFlow<Navigator.Direction> = _navActions

    override fun navigateTo(navAction: NavigationAction) {
        _navActions.tryEmit(Navigator.Direction.Forward(navAction))
    }

    override fun navigateUp() {
        _navActions.tryEmit(Navigator.Direction.Back)
    }
}