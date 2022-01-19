package com.teamfillin.fillin.presentation.onboarding

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.viewpager2.widget.ViewPager2
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.local.FillInDataStore
import com.teamfillin.fillin.databinding.ActivityOnboardingBinding
import com.teamfillin.fillin.presentation.home.HomeActivity
import com.teamfillin.fillin.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity :
    BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    @Inject
    lateinit var dataStore: FillInDataStore

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        if (dataStore.isOnboardingShown)
            startActivity(LoginActivity.getIntent(this@OnboardingActivity))
        val adapter = OnboardingAdapter(this)
        binding.viewpagerOnboaring.adapter = adapter
        binding.dotOnboarding.setViewPager2(binding.viewpagerOnboaring)
        binding.viewpagerOnboaring.registerOnPageChangeCallback(
            object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    binding.txtOnbardingSkip.isVisible = position != 2
                    binding.txtOnbardingStart.isVisible = position == 2
                    binding.txtOnbardingNext.isVisible = position != 2
                }
            }
        )
        with(binding) {
            txtOnbardingNext.setOnSingleClickListener {
                binding.viewpagerOnboaring.setCurrentItem(
                    binding.viewpagerOnboaring.currentItem + 1,
                    true
                )
            }
            txtOnbardingStart.setOnSingleClickListener {
                dataStore.isOnboardingShown = true
                startActivity(LoginActivity.getIntent(this@OnboardingActivity))
            }
            txtOnbardingSkip.setOnSingleClickListener {
                dataStore.isOnboardingShown = true
                startActivity(LoginActivity.getIntent(this@OnboardingActivity))
            }
        }
    }
}