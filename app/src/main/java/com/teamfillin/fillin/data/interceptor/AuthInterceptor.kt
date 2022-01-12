package com.teamfillin.fillin.data.interceptor

import com.google.gson.Gson
import com.teamfillin.fillin.config.di.RetrofitModule.BASE_URL
import com.teamfillin.fillin.data.local.FillInDataStore
import com.teamfillin.fillin.data.response.ResponseRefresh
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val localStorage: FillInDataStore,
    private val gson: Gson
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest = if (isLogin(originalRequest)) originalRequest else
            originalRequest.newBuilder().addHeader("token", localStorage.userToken).build()
        val response = chain.proceed(authRequest)
        when (response.code) {
            401 -> {
                val refreshTokenRequest = originalRequest.newBuilder().get()
                    .url("${BASE_URL}auth/token")
                    .addHeader("accesstoken", localStorage.userToken)
                    .addHeader("refreshtoken", localStorage.refreshToken)
                    .build()
                val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                if (refreshTokenResponse.isSuccessful) {
                    val refreshToken = gson.fromJson(
                        refreshTokenResponse.body?.string(),
                        ResponseRefresh::class.java
                    )
                    with(localStorage) {
                        userToken = refreshToken.tokens.newAccessToken
                        this.refreshToken = refreshToken.tokens.refreshToken
                    }
                    val newRequest = originalRequest.newBuilder()
                        .addHeader("token", localStorage.userToken)
                        .build()
                    return chain.proceed(newRequest)
                }
            }
        }
        return response
    }

    private fun isLogin(originalRequest: Request) =
        !originalRequest.url.encodedPath.contains("token") &&
                originalRequest.url.encodedPath.contains("auth")

    private fun Interceptor.Chain.proceedDeletingTokenOnError(request: Request): Response {
        val response = proceed(request)
        if (response.code == 401) {
            localStorage.userToken = ""
        }
        return response
    }
}