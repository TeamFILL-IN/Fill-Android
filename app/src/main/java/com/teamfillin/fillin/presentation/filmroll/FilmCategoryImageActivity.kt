package com.teamfillin.fillin.presentation.filmroll

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.view.get
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.data.CategoryInfo
import com.teamfillin.fillin.databinding.ActivityCategoryImageBinding
import com.teamfillin.fillin.databinding.ActivityFilmRollCategoryBinding
import com.teamfillin.fillin.presentation.category.FilmCategoryAdapter
import com.teamfillin.fillin.presentation.map.CustomDecoration

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
                // 데이터 넘겨주기

            }
        })
    }


    private fun addFilmImageList() {
        filmImageAdapter.submitList(
            listOf(
                FilmImageInfo(
                    image = R.drawable.and_card_img
                ),
                FilmImageInfo(
                    image = R.drawable.and_card_img
                ), FilmImageInfo(
                    image = R.drawable.and_card_img
                ), FilmImageInfo(
                    image = R.drawable.and_card_img
                ), FilmImageInfo(
                    image = R.drawable.and_card_img
                ), FilmImageInfo(
                    image = R.drawable.and_card_img
                ), FilmImageInfo(
                    image = R.drawable.and_card_img
                )
            )
        )

    }
}
