package com.teamfillin.fillin.presentation.my

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {
    @Inject
    lateinit var userService: UserService
    lateinit var myPagePhotoService: MyPagePhotoService


    private lateinit var adapter: MyPagePhotoRecyclerViewAdapter
    var photoDatas = listOf<Photos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnBackHome.setOnClickListener {
            finish()
        }

        initializelist()
        initPhotoRecyclerView()
        showUserInfo()
    }

    private fun showUserInfo(){
        val call: Call<BaseResponse<ResponseUserInfo>> = userService.getUserInfo()

        call.enqueueUtil(
            onSuccess={data->
                binding.tvNickname.text= data?.data.user.id.toString()
                Glide.with(this@MyPageActivity)
                    .load(data?.data.user.imageUrl)
                    .circleCrop()
                    .into(binding.ivProfile)
            }
        )
    }

    private fun initializelist() {
        photoDatas = listOf(
            Photos(R.drawable.and_photo_rectangle),
            Photos(R.drawable.and_photo_rectangle),
            Photos(R.drawable.and_photo_rectangle),
            Photos(R.drawable.and_photo_rectangle),
            Photos(R.drawable.and_photo_rectangle),
            Photos(R.drawable.and_photo_rectangle)
        )
    }

    private fun initPhotoRecyclerView() {
        adapter = MyPagePhotoRecyclerViewAdapter()
        adapter.replaceList(photoDatas)
        binding.rvMyPage.adapter = adapter
        val gridLayoutManager = GridLayoutManager(this, 3)
        binding.rvMyPage.layoutManager = gridLayoutManager
    }
}