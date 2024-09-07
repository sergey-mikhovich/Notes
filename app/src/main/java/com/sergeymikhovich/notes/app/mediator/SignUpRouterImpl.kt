package com.sergeymikhovich.notes.app.mediator

import com.sergeymikhovich.notes.core.common.navigation.Navigator
import com.sergeymikhovich.notes.feature.auth.account_center.navigation.AccountCenterDirection
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInDirection
import com.sergeymikhovich.notes.feature.auth.sign_up.navigation.SignUpDirection
import com.sergeymikhovich.notes.feature.auth.sign_up.navigation.SignUpRouter
import com.sergeymikhovich.notes.feature.notes.navigation.NotesDirection
import javax.inject.Inject

class SignUpRouterImpl @Inject constructor(
    private val navigator: Navigator
): SignUpRouter {

    override fun toNotes() {
        navigator.navigateTo(NotesDirection.createActionAndPopUpTo(SignUpDirection.route))
    }

    override fun toSignIn() {
        navigator.navigateTo(SignInDirection.createAction())
    }

    override fun back() {
        navigator.navigateUp()
    }
}