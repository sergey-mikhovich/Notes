package com.sergeymikhovich.notes.app.mediator

import com.sergeymikhovich.notes.core.common.navigation.Navigator
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInDirection
import com.sergeymikhovich.notes.feature.notes.navigation.NotesDirection
import com.sergeymikhovich.notes.feature.splash.navigation.SplashDirection
import com.sergeymikhovich.notes.feature.splash.navigation.SplashRouter
import javax.inject.Inject

class SplashRouterImpl @Inject constructor(
    private val navigator: Navigator
): SplashRouter {

    override fun toSignIn() {
        navigator.navigateTo(
            SignInDirection.createActionAndPopUpTo(SplashDirection.route)
        )
    }

    override fun toNotes() {
        navigator.navigateTo(
            NotesDirection.createActionAndPopUpTo(SplashDirection.route)
        )
    }
}