package com.teamfillin.fillin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil.setContentView
import com.teamfillin.fillin.databinding.ActivityHomeBinding
import kotlinx.coroutines.newFixedThreadPoolContext

class HomeActivity : AppCompatActivity(){
    private lateinit var binding :ActivityHomeBinding
    private lateinit var newPhotosAdapter : NewPhotosAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityHomeBinding.inflate(layoutInflater)
        initAdapter()
        setContentView(binding.root)
    }

    private fun initAdapter(){
        newPhotosAdapter = NewPhotosAdapter()

        binding.rcvNewPhotos.adapter= newPhotosAdapter

        val exphoto = R.drawable.and_photo_rectangle

        newPhotosAdapter.photolist.addAll(
            listOf(
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto),
                NewPhotosData(exphoto)
            )
        )
        newPhotosAdapter.notifyDataSetChanged()
    }
}