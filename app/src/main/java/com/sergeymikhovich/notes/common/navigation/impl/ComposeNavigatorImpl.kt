package com.sergeymikhovich.notes.common.navigation.impl

import com.sergeymikhovich.notes.common.navigation.api.NavigationAction
import com.sergeymikhovich.notes.common.navigation.api.Navigator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

class ComposeNavigatorImpl @Inject constructor() : Navigator {

    private val _navActions: MutableSharedFlow<Navigator.Direction> = MutableSharedFlow(1)
    override val navActions: SharedFlow<Navigator.Direction> = _navActions

    override fun navigateTo(navAction: NavigationAction) {
        _navActions.tryEmit(Navigator.Direction.Forward(navAction))
    }

    override fun navigateUp() {
        _navActions.tryEmit(Navigator.Direction.Back)
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface NavigatorModule {

    @Binds
    @Singleton
    fun bindNavigator(navigator: ComposeNavigatorImpl): Navigator
}