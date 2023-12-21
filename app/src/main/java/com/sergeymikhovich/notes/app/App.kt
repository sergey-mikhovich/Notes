package com.sergeymikhovich.notes.app

import android.app.Application
import com.sergeymikhovich.notes.common.di.component.DaggerApplicationComponent

class App : Application() {

    val appComponent by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}