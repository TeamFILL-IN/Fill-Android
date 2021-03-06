package com.teamfillin.fillin.config.initializer

import android.content.Context
import androidx.startup.Initializer
import com.kakao.sdk.common.KakaoSdk
import com.teamfillin.fillin.BuildConfig

class KakaoAuthInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        KakaoSdk.init(context, "a7ddbcd24d7fff22320cc13a1e534104")
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(TimberInitializer::class.java)
    }
}