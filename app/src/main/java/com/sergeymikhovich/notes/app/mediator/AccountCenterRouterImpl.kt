package com.sergeymikhovich.notes.app.mediator

import com.sergeymikhovich.notes.core.common.navigation.Navigator
import com.sergeymikhovich.notes.feature.auth.account_center.navigation.AccountCenterDirection
import com.sergeymikhovich.notes.feature.auth.account_center.navigation.AccountCenterRouter
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInDirection
import com.sergeymikhovich.notes.feature.auth.sign_up.navigation.SignUpDirection
import com.sergeymikhovich.notes.feature.splash.navigation.SplashDirection
import javax.inject.Inject

class AccountCenterRouterImpl @Inject constructor(
    private val navigator: Navigator
): AccountCenterRouter {

    override fun toSignIn() {
        navigator.navigateTo(SignInDirection.createAction())
    }

    override fun toSignUp() {
        navigator.navigateTo(SignUpDirection.createAction())
    }

    override fun toSplash() {
        navigator.navigateTo(SplashDirection.createActionAndPopUpTo(AccountCenterDirection.route))
    }

    override fun back() {
        navigator.navigateUp()
    }
}