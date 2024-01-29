package com.sergeymikhovich.notes.common.di.component

import android.app.Activity
import android.content.Context
import com.sergeymikhovich.notes.app.database.AppDatabaseModule
import com.sergeymikhovich.notes.common.di.context.CoroutineContextModule
import com.sergeymikhovich.notes.common.di.context.DispatchersModule
import com.sergeymikhovich.notes.common.di.scope.ApplicationScope
import com.sergeymikhovich.notes.common.navigation.impl.NavigatorModule
import com.sergeymikhovich.notes.feature.data.note.impl.LocalDataSourceModule
import com.sergeymikhovich.notes.feature.data.note.impl.NoteRepositoryModule
import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteDaoModule
import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteMapperModule
import com.sergeymikhovich.notes.feature.domain.note.impl.di.NoteUseCasesModule
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
    modules = [
        NoteUseCasesModule::class,
        NoteRepositoryModule::class,
        LocalDataSourceModule::class,
        NoteDaoModule::class,
        NoteMapperModule::class,
        AppDatabaseModule::class,
        CoroutineContextModule::class,
        DispatchersModule::class,
        NavigatorModule::class
    ]
)
interface ApplicationComponent {
    fun inject(activity: Activity)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): ApplicationComponent
    }
}