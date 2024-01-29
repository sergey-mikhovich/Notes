package com.sergeymikhovich.notes.common.navigation.impl

import com.sergeymikhovich.notes.common.di.scope.ApplicationScope
import com.sergeymikhovich.notes.common.navigation.api.NavigationAction
import com.sergeymikhovich.notes.common.navigation.api.Navigator
import dagger.Binds
import dagger.Module
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class ComposeNavigatorImpl @Inject constructor() : Navigator {

    private val _navActions: MutableSharedFlow<Navigator.Direction> = MutableSharedFlow()
    override val navActions: SharedFlow<Navigator.Direction> = _navActions

    override fun navigateTo(navAction: NavigationAction) {
        _navActions.tryEmit(Navigator.Direction.Forward(navAction))
    }

    override fun navigateUp() {
        _navActions.tryEmit(Navigator.Direction.Back)
    }
}

@Module
interface NavigatorModule {

    @Binds
    @ApplicationScope
    fun bindNavigator(navigator: ComposeNavigatorImpl): Navigator
}