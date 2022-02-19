package com.teamfillin.fillin.presentation.my

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kakao.sdk.user.UserApiClient
import com.teamfillin.fillin.BuildConfig
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.service.MyPagePhotoService
import com.teamfillin.fillin.data.service.UserService
import com.teamfillin.fillin.databinding.ActivityMyPageBinding
import com.teamfillin.fillin.presentation.dialog.PhotoDialogFragment
import com.teamfillin.fillin.presentation.login.KakaoAuthService
import com.teamfillin.fillin.presentation.map.SpaceDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {
    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var myPhotoService: MyPagePhotoService

    @Inject
    lateinit var client: UserApiClient

    private val adapter = MyPagePhotoRecyclerViewAdapter {
        val dialog = PhotoDialogFragment()
        Timber.d("mypagekangmin")
        val bundle = Bundle().apply { putString("photoUrl", it.imageUrl) }
        dialog.apply {
            arguments = bundle
            show(supportFragmentManager, "dialog")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.isDebug = BuildConfig.DEBUG
        binding.btnBackHome.setOnClickListener {
            finish()
        }
        binding.btnSettingDebug.setOnSingleClickListener {
            client.loginWithKakaoAccount(this, callback = { token, _ ->
                MaterialAlertDialogBuilder(this)
                    .setTitle("KAKAO AUTH TOKEN")
                    .setMessage("${token?.accessToken}")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }.show()
            })
        }
        binding.btnSetting.setOnClickListener {
            startActivity(Intent(this, OssLicensesMenuActivity::class.java))
        }

        initAdapter()
        showUserInfo()
        showMyPhoto()
    }

    private fun initAdapter() {
        binding.rvMyPage.adapter = adapter
        val spaceDecoration = SpaceDecoration(7)
        binding.rvMyPage.addItemDecoration(spaceDecoration)
    }

    private fun showUserInfo() {
        lifecycleScope.launch {
            runCatching {
                userService.getUserInfo().await()
            }.onSuccess {
                binding.tvNickname.text = it.data.user.nickname
                Glide.with(this@MyPageActivity)
                    .load(it.data.user.imageUrl)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .circleCrop()
                    .into(binding.ivProfile)
            }.onFailure(Timber::e)
        }
    }

    private fun showMyPhoto() {
        lifecycleScope.launch {
            runCatching {
                myPhotoService.getUserPhotos().await()
            }.onSuccess {
                adapter.replaceList(it.data.photos)
            }.onFailure(Timber::e)
        }
    }
}