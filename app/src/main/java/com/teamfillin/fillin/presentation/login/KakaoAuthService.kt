package com.teamfillin.fillin.presentation.login

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

class KakaoAuthService @Inject constructor(
    @ActivityContext private val context: Context,
    private val client: UserApiClient
) {
    val isKakaoTalkLoginAvailable: Boolean
        get() = client.isKakaoTalkLoginAvailable(context)
    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Init)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()


    fun loginByKakaoTalk() {
        client.loginWithKakaoTalk(context) { token, error ->
            error?.let {
                _loginState.value = LoginState.Failure(it)
                return@loginWithKakaoTalk
            }
            token?.let { _loginState.value = LoginState.Success(it.accessToken) }
        }
    }

    fun loginByKakaoAccount() {
        client.loginWithKakaoAccount(context) { token, error ->
            error?.let {
                _loginState.value = LoginState.Failure(it)
                return@loginWithKakaoAccount
            }
            token?.let { _loginState.value = LoginState.Success(it.accessToken) }
        }
    }

    fun logout() {
        client.logout(Timber::e)
    }

    sealed class LoginState {
        object Init : LoginState()
        data class Success(val token: String) : LoginState()
        data class Failure(val error: Throwable) : LoginState()
    }
}