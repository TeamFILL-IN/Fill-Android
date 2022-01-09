package com.teamfillin.fillin.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.snackbar.Snackbar
import com.teamfillin.fillin.databinding.ActivityHomeBinding
import com.teamfillin.fillin.presentation.login.KakaoAuthService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    @Inject
    lateinit var kakaoAuthService: KakaoAuthService

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
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