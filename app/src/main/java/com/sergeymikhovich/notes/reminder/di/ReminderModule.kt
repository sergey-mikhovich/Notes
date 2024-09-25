package com.sergeymikhovich.notes.reminder.di

import com.sergeymikhovich.notes.reminder.ReminderManager
import com.sergeymikhovich.notes.reminder.WorkManagerReminderManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ReminderModule {

    @Binds
    fun bindReminderManager(reminderManager: WorkManagerReminderManager): ReminderManager
}