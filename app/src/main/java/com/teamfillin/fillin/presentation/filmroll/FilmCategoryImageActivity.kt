package com.teamfillin.fillin.presentation.filmroll

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.databinding.ActivityCategoryImageBinding
import com.teamfillin.fillin.presentation.filmroll.add.AddPhotoActivity

class FilmCategoryImageActivity :
    BindingActivity<ActivityCategoryImageBinding>(R.layout.activity_category_image) {

    private val filmImageAdapter = FilmImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        click()
        setFilmImageAdapter()
    }

    private fun setFilmImageAdapter() {
        binding.rvCategoryImage.adapter = filmImageAdapter
        addFilmImageList()
    }

    private fun click() {
        filmImageAdapter.setItemClickListener(object : FilmImageAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                // Todo 아이템 클릭 시 이미지 상세보기 창 띄우기

            }
        })
        binding.fabAddPhoto.setOnSingleClickListener {
            val intent = Intent(this, AddPhotoActivity::class.java)
            startActivity(intent)
        }
        binding.btnBack.setOnSingleClickListener {
            finish()
        }
    }


    private fun addFilmImageList() {
        filmImageAdapter.submitList(
            listOf(
                FilmImageInfo(
                    image = R.drawable.and_card_img
                ),
                FilmImageInfo(
                    image = R.drawable.and_card_long_img
                ), FilmImageInfo(
                    image = R.drawable.and_card_img
                ), FilmImageInfo(
                    image = R.drawable.and_card_img
                ), FilmImageInfo(
                    image = R.drawable.and_card_img
                ), FilmImageInfo(
                    image = R.drawable.and_card_long_img
                ), FilmImageInfo(
                    image = R.drawable.and_card_long_img
                )
            )
        )

    }
}
