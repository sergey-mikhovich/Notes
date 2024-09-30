package com.sergeymikhovich.notes.app.mediator.di

import com.sergeymikhovich.notes.app.mediator.AccountCenterRouterImpl
import com.sergeymikhovich.notes.app.mediator.SignInRouterImpl
import com.sergeymikhovich.notes.app.mediator.NoteRouterImpl
import com.sergeymikhovich.notes.app.mediator.NotesRouterImpl
import com.sergeymikhovich.notes.app.mediator.SignUpRouterImpl
import com.sergeymikhovich.notes.feature.auth.account_center.navigation.AccountCenterRouter
import com.sergeymikhovich.notes.feature.auth.sign_in.navigation.SignInRouter
import com.sergeymikhovich.notes.feature.auth.sign_up.navigation.SignUpRouter
import com.sergeymikhovich.notes.feature.note.navigation.NoteRouter
import com.sergeymikhovich.notes.feature.notes.navigation.NotesRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface NoteRouterModule {

    @Binds
    fun bindsNoteRouter(noteRouter: NoteRouterImpl): NoteRouter

    @Binds
    fun bindsNotesRouter(notesRouter: NotesRouterImpl): NotesRouter

    @Binds
    fun bindsSignInRouter(googleSignInRouter: SignInRouterImpl): SignInRouter

    @Binds
    fun bindsSignUpRouter(signUpRouter: SignUpRouterImpl): SignUpRouter

    @Binds
    fun bindsAccountCenterRouter(accountCenterRouter: AccountCenterRouterImpl): AccountCenterRouter
}