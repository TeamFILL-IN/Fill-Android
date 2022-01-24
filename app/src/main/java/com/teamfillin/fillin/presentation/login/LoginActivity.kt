package com.teamfillin.fillin.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.databinding.ActivityLoginBinding
import com.teamfillin.fillin.presentation.home.HomeActivity
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
        binding.containerLoginKakao.setOnClickListener {
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
                        is KakaoAuthService.LoginState.Success -> viewModel.login(it.token, it.id)
                        is KakaoAuthService.LoginState.Failure -> Timber.d("Kakao Login Failed ${it.error}")
                        else -> Timber.d("Kakao INIT")
                    }
                }
        }

        lifecycleScope.launch {
            viewModel.loginResult
                .flowWithLifecycle(lifecycle)
                .collect {
                    when (it) {
                        is LoginViewModel.InHouseLoginState.Success -> {
                            startActivity(HomeActivity.getIntent(this@LoginActivity))
                        }
                        is LoginViewModel.InHouseLoginState.Failure -> toast(it.message)
                    }
                }
        }
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, LoginActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}