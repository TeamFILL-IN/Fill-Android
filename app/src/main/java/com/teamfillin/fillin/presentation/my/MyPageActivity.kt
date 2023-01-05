package com.teamfillin.fillin.presentation.my

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kakao.sdk.user.UserApiClient
import com.teamfillin.fillin.BuildConfig
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.core.view.UiState
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.service.UserService
import com.teamfillin.fillin.databinding.ActivityMyPageBinding
import com.teamfillin.fillin.presentation.dialog.PhotoDialogFragment
import com.teamfillin.fillin.presentation.map.SpaceDecoration
import com.teamfillin.fillin.presentation.my.terms.TermsActivity
import com.teamfillin.fillin.presentation.my.update.ProfileUpdateActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {
    private val viewModel by viewModels<MyPageViewModel>()

    @Inject
    lateinit var userService: UserService

    @Inject
    lateinit var client: UserApiClient

    private val adapter = MyPagePhotoRecyclerViewAdapter {
        val dialog = PhotoDialogFragment()
        val bundle = Bundle().apply { putString("photoUrl", it.imageUrl) }
        dialog.apply {
            arguments = bundle
            show(supportFragmentManager, "dialog")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initView()
        initEvent()
        initAdapter()
        observe()

    }

    private fun initView() {
        binding.isDebug = BuildConfig.DEBUG
        viewModel.getUser()
        viewModel.getUserPhotos()
    }

    private fun initEvent() {
        binding.btnBackHome.setOnClickListener {
            finish()
        }

        binding.ivEdit.setOnSingleClickListener {
            Intent(this, ProfileUpdateActivity::class.java).apply {
                startActivity(this)
            }
        }

        binding.ivUp.setOnSingleClickListener {
            binding.rvMyPage.isVisible = !binding.rvMyPage.isVisible
            imageRotation(binding.ivUp, binding.rvMyPage.isVisible)
        }

        binding.tvTerms.setOnSingleClickListener {
            Intent(this, TermsActivity::class.java).apply {
                startActivity(this)
            }
        }
        binding.btnSettingDebug.setOnSingleClickListener {
            client.loginWithKakaoAccount(this, callback = { token, _ ->
                MaterialAlertDialogBuilder(this)
                    .setTitle("KAKAO AUTH TOKEN")
                    .setMessage("${token?.accessToken}")
                    .setPositiveButton("OK") { dialog, _ ->
                        client.me { user, _ ->
                            getSystemService(ClipboardManager::class.java).apply {
                                setPrimaryClip(
                                    ClipData.newPlainText(
                                        "Kakao Token",
                                        "token: ${token?.accessToken} idkey: ${user?.id}"
                                    )
                                )
                            }
                        }
                        dialog.dismiss()
                    }.show()
            })
        }
    }

    private fun initAdapter() {
        binding.rvMyPage.adapter = adapter
        val spaceDecoration = SpaceDecoration(7)
        binding.rvMyPage.addItemDecoration(spaceDecoration)
    }

    private fun observe() {
        viewModel.userInfo.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> {
                        binding.tvNickname.text = it.data.nickname
                        Glide.with(this@MyPageActivity)
                            .load(it.data.imageUrl)
                            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                            .circleCrop()
                            .into(binding.ivProfile)
                    }
                    is UiState.Failure -> {
                        toast(it.msg)
                    }
                    else -> {}
                }
            }.launchIn(lifecycleScope)

        viewModel.myPhotos.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> {
                        adapter.replaceList(it.data)
                    }
                    is UiState.Failure -> {
                        toast(it.msg)
                    }
                    else -> {}
                }
            }.launchIn(lifecycleScope)
    }

    private fun imageRotation(image: ImageView, isExpand: Boolean) {
        if (isExpand) {
            image.animate().setDuration(200).rotation(0f)
        } else {
            image.animate().setDuration(200).rotation(180f)
        }
    }
}