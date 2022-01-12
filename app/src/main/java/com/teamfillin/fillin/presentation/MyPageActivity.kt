package com.teamfillin.fillin.presentation

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.teamfillin.fillin.MyPagePhotoRecyclerViewAdapter
import com.teamfillin.fillin.Photos
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.databinding.ActivityMyPageBinding

class MyPageActivity : BindingActivity<ActivityMyPageBinding>(R.layout.activity_my_page) {
    private lateinit var adapter: MyPagePhotoRecyclerViewAdapter
    var photoDatas = listOf<Photos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initializelist()
        initPhotoRecyclerView()
        setProfile()
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


    private fun setProfile() {
        Glide.with(this)
            .load(R.drawable.profile)
            .circleCrop()
            .into(binding.ivProfile)
        //TODO by현지: 확장함수에 CircleCrop등 기능 추가되면 바꿔보기
    }
}