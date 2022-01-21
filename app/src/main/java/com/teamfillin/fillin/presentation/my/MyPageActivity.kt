package com.teamfillin.fillin.presentation.my

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.teamfillin.fillin.presentation.add.Photos
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseUserInfo
import com.teamfillin.fillin.data.service.MyPagePhotoService
import com.teamfillin.fillin.data.service.UserService
import com.teamfillin.fillin.databinding.ActivityMyPageBinding
import com.teamfillin.fillin.enqueueUtil
import com.teamfillin.fillin.presentation.dialog.PhotoDialogFragment
import com.teamfillin.fillin.presentation.map.SpaceDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {
    @Inject
    lateinit var userService: UserService
    @Inject
    lateinit var myPhotoService: MyPagePhotoService
    private val adapter = MyPagePhotoRecyclerViewAdapter{
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

        binding.btnBackHome.setOnClickListener {
            finish()
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