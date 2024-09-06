package com.sergeymikhovich.notes.feature.auth.account_center.navigation

interface AccountCenterRouter {
    fun toSignIn()
    fun toSignUp()
    fun toSplash()
    fun back()
}