package com.teamfillin.fillin.presentation.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teamfillin.fillin.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    @Inject
    lateinit var kakaoAuthService: KakaoAuthService

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                        is KakaoAuthService.LoginState.Success -> Timber.d("Kakao Login Success ${it.token}")
                        is KakaoAuthService.LoginState.Failure -> Timber.d("Kakao Login Failed ${it.error}")
                        else -> Timber.d("Kakao INIT")
                    }
                }
        }
    }
}