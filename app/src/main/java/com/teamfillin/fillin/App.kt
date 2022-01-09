package com.teamfillin.fillin

import android.app.Application
import com.teamfillin.fillin.design.ResolutionMetrics
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var metrics: ResolutionMetrics
    override fun onCreate() {
        super.onCreate()
        FlipperInitializer.init(this)
        App.resolutionMetrics = metrics
    }

    companion object {
        lateinit var resolutionMetrics: ResolutionMetrics
    }
}
