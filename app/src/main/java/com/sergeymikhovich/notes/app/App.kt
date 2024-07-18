package com.sergeymikhovich.notes.app

import android.app.Application
import com.sergeymikhovich.notes.sync.initializers.Sync
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Sync.initialize(this)
    }
}