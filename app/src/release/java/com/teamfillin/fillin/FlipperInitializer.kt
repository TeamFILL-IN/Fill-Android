package com.teamfillin.fillin

import android.app.Application
import okhttp3.OkHttpClient

object FlipperInitializer {
    fun init(app: Application) {}
    fun addFlipperNetworkPlguin(builder: OkHttpClient.Builder) = builder
}