package com.teamfillin.fillin.presentation.category

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.get
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.teamfillin.fillin.R
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NEW_PHOTOS_TYPE
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NEXT_BUTTON_TYPE
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NewPhotosData
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.data.CategoryInfo
import com.teamfillin.fillin.data.ResponseLocationInfo
import com.teamfillin.fillin.databinding.ActivityFilmRollCategoryBinding
import com.teamfillin.fillin.presentation.AddPhotoActivity
import com.teamfillin.fillin.presentation.map.CustomDecoration
import com.teamfillin.fillin.presentation.map.LocationListAdapter
import com.teamfillin.fillin.presentation.map.MapSearchActivity

class FilmRollCategoryActivity :
    BindingActivity<ActivityFilmRollCategoryBinding>(R.layout.activity_film_roll_category) {
    private val filmCategoryAdapter = FilmCategoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        click()
        setFilmCategoryAdapter()
    }

    private fun setFilmCategoryAdapter() {
        binding.rvCategory.adapter = filmCategoryAdapter
        val customDecoration = CustomDecoration(2f, 10f, Color.GRAY)
        binding.rvCategory.addItemDecoration(customDecoration)
        addFilmCategoryList()
    }

    private fun click() {
        filmCategoryAdapter.setItemClickListener { v, position -> // 데이터 넘겨주기
            binding.rvCategory[position].setBackgroundResource(R.color.dark_grey_2)
        }
    }


    private fun addFilmCategoryList() {
        filmCategoryAdapter.submitList(
            listOf(
                CategoryInfo(
                    film = "Kodak color plus 200 abcdefg1234567"
                ),
                CategoryInfo(
                    film = "Kodak color plus 200 abcdefg1234567"
                ),
                CategoryInfo(
                    film = "Kodak color plus 200 abcdefg1234567"
                ),
                CategoryInfo(
                    film = "Kodak color plus 200 abcdefg1234567"
                ),
                CategoryInfo(
                    film = "Kodak color plus 200 abcdefg1234567"
                ),
                CategoryInfo(
                    film = "Kodak color plus 200 abcdefg1234567"
                ),
                CategoryInfo(
                    film = "Kodak color plus 200 abcdefg1234567"
                ),
                CategoryInfo(
                    film = "Kodak color plus 200 abcdefg1234567"
                )
            )
        )
    }
}
