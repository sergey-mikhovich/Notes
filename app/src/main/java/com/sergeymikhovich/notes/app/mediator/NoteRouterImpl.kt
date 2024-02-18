package com.sergeymikhovich.notes.app.mediator

import com.sergeymikhovich.notes.common.navigation.api.Navigator
import com.sergeymikhovich.notes.feature.presentation.note.navigation.NoteRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Inject

class NoteRouterImpl @Inject constructor(
    private val navigator: Navigator
): NoteRouter {

    override fun back() {
        navigator.navigateUp()
    }
}

@Module
@InstallIn(ViewModelComponent::class)
interface NoteRouterModule {

    @Binds
    fun bindNoteRouter(noteRouter: NoteRouterImpl): NoteRouter
}