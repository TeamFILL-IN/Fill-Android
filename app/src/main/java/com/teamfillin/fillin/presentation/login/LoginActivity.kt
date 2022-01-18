package com.teamfillin.fillin.presentation.login

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    @Inject
    lateinit var kakaoAuthService: KakaoAuthService
    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding.txtHello.setOnClickListener {
            if (kakaoAuthService.isKakaoTalkLoginAvailable) {
                kakaoAuthService.loginByKakaoTalk()
            } else {
                kakaoAuthService.loginByKakaoAccount()
            }
        }

        lifecycleScope.launch {
            kakaoAuthService.loginState
                .flowWithLifecycle(lifecycle)
                .collect {
                    when (it) {
                        is KakaoAuthService.LoginState.Success -> viewModel.login(it.token)
                        is KakaoAuthService.LoginState.Failure -> Timber.d("Kakao Login Failed ${it.error}")
                        else -> Timber.d("Kakao INIT")
                    }
                }
        }

        lifecycleScope.launch {
            viewModel.loginResult
                .flowWithLifecycle(lifecycle)
                .collect {
                    Timber.d("Nunu inHouseLogin $it")
                    when (it) {
                        is LoginViewModel.InHouseLoginState.Success -> toast("로그인 성공")
                        is LoginViewModel.InHouseLoginState.Failure -> toast(it.message)
                    }
                }
        }
    }
}