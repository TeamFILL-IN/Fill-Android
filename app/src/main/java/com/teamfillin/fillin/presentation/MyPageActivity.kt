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
    val photoDatas=mutableListOf<Photos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        initializelist()
        initPhotoRecyclerView()
        setProfile()
    }

    private fun initializelist(){
        with(photoDatas){
            add(Photos(R.drawable.and_photo_rectangle))
            add(Photos(R.drawable.and_photo_rectangle))
            add(Photos(R.drawable.and_photo_rectangle))
            add(Photos(R.drawable.and_photo_rectangle))
            add(Photos(R.drawable.and_photo_rectangle))
            add(Photos(R.drawable.and_photo_rectangle))
        }
    }

    private fun initPhotoRecyclerView(){
        adapter= MyPagePhotoRecyclerViewAdapter()
        adapter.photolist=photoDatas
        binding.rvMyPage.adapter=adapter
        val gridLayoutManager=GridLayoutManager(this,3)
        binding.rvMyPage.layoutManager=gridLayoutManager
    }

    private fun setProfile(){
        Glide.with(this)
            .load(R.drawable.profile)
            .circleCrop()
            .into(binding.ivProfile)
    }
}