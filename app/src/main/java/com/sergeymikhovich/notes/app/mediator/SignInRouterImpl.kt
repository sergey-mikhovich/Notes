package com.sergeymikhovich.notes.app.mediator

import com.sergeymikhovich.notes.core.common.navigation.Navigator
import com.sergeymikhovich.notes.feature.auth.account_center.navigation.AccountCenterDirection
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInDirection
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInRouter
import com.sergeymikhovich.notes.feature.auth.sign_up.navigation.SignUpDirection
import com.sergeymikhovich.notes.feature.notes.navigation.NotesDirection
import javax.inject.Inject

class SignInRouterImpl @Inject constructor(
    private val navigator: Navigator
): SignInRouter {

    override fun toNotes() {
        navigator.navigateTo(NotesDirection.createActionAndPopUpTo(SignInDirection.route))
    }

    override fun toSignUp() {
        navigator.navigateTo(SignUpDirection.createAction())
    }

    override fun back() {
        navigator.navigateUp()
    }
}