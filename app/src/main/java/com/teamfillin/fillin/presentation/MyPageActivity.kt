package com.teamfillin.fillin.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.teamfillin.fillin.MyPagePhotoRecyclerViewAdapter
import com.teamfillin.fillin.Photos
import com.teamfillin.fillin.R
import com.teamfillin.fillin.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyPageBinding
    private lateinit var adapter: MyPagePhotoRecyclerViewAdapter
    val photoDatas=mutableListOf<Photos>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initializelist()
        initPhotoRecyclerView()
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
}