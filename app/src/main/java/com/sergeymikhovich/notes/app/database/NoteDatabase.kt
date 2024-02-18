package com.sergeymikhovich.notes.app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sergeymikhovich.notes.app.database.AppDatabase.Companion.DB_NAME
import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteDatabase
import com.sergeymikhovich.notes.feature.data.note.impl.db.NoteEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Database(
    entities = [NoteEntity::class],
    version = 1
)
abstract class AppDatabase: RoomDatabase(), NoteDatabase {
    companion object {
        const val DB_NAME = "NoteDatabase"
    }
}

@Module
@InstallIn(SingletonComponent::class)
class AppDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext appContext: Context
    ): NoteDatabase {
        return Room.databaseBuilder(appContext, AppDatabase::class.java, DB_NAME).build()
    }
}