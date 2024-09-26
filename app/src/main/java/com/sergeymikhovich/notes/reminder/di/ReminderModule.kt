package com.sergeymikhovich.notes.reminder.di

import com.sergeymikhovich.notes.reminder.ReminderScheduler
import com.sergeymikhovich.notes.reminder.ReminderSchedulerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ReminderModule {

    @Binds
    fun bindReminderScheduler(reminderScheduler: ReminderSchedulerImpl): ReminderScheduler
}