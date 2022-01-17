package com.teamfillin.fillin.presentation.category

import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.get
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.data.CategoryInfo
import com.teamfillin.fillin.databinding.ActivityFilmRollCategoryBinding
import com.teamfillin.fillin.presentation.map.CustomDecoration

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
        filmCategoryAdapter.setItemClickListener(object : FilmCategoryAdapter.OnItemClickListener {
            override fun onClick(v: View, position: Int) {
                // 데이터 넘겨주기
                binding.rvCategory[position].setBackgroundResource(R.color.dark_grey_2)
                toast("${filmCategoryAdapter.currentList[position].film}")
            }
        })
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

    //이거뭔코드인지,..?
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}
