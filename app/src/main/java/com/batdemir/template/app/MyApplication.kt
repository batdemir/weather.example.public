package com.batdemir.template.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.batdemir.template.BuildConfig
import com.batdemir.template.di.component.ApplicationComponent
import com.batdemir.template.di.component.DaggerApplicationComponent
import timber.log.Timber

class MyApplication : Application() {
    val applicationComponent: ApplicationComponent by lazy {
        DaggerApplicationComponent.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupTheme()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}