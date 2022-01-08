package com.teamfillin.fillin

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        FlipperInitializer.init(this)
    }
}
