package com.teamfillin.fillin

import android.app.Application
import com.teamfillin.fillin.design.ResolutionMetrics
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {
    @Inject
    lateinit var resolutionMetrics: ResolutionMetrics
    override fun onCreate() {
        super.onCreate()
        FlipperInitializer.init(this)
        App.resolutionMetrics = this.resolutionMetrics
    }

    companion object {
        lateinit var resolutionMetrics: ResolutionMetrics
    }
}
