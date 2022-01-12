package com.teamfillin.fillin.data.interceptor

import com.teamfillin.fillin.data.local.FillInDataStore
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val localStorage: FillInDataStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val authRequest = chain.request().newBuilder()
            .addHeader("token", localStorage.userToken).build()
        return chain.proceed(authRequest)
    }
}