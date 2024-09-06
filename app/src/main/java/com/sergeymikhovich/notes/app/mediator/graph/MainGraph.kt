package com.sergeymikhovich.notes.app.mediator.graph

import androidx.navigation.NavGraphBuilder
import com.sergeymikhovich.notes.feature.auth.account_center.composableToAccountCenter
import com.sergeymikhovich.notes.feature.auth.sign_in.composableToSignIn
import com.sergeymikhovich.notes.feature.auth.sign_up.composableToSignUp
import com.sergeymikhovich.notes.feature.note.composeToNote
import com.sergeymikhovich.notes.feature.notes.composableToNotes
import com.sergeymikhovich.notes.feature.splash.composableToSplash

fun NavGraphBuilder.composableAll() {
    composableToSplash()
    composableToSignIn()
    composableToSignUp()
    composableToAccountCenter()
    composableToNotes()
    composeToNote()
}