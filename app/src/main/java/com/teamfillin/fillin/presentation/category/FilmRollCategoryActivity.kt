package com.teamfillin.fillin.presentation.category

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.core.view.get
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.CategoryInfo
import com.teamfillin.fillin.databinding.ActivityFilmRollCategoryBinding
import com.teamfillin.fillin.presentation.filmroll.FilmCategoryImageActivity
import com.teamfillin.fillin.presentation.filmroll.FilmRollActivity
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
        filmCategoryAdapter.setItemClickListener { film ->
            val intent = Intent(this, FilmRollActivity::class.java)
            intent.putExtra("id", film)
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.btnBack.setOnSingleClickListener {
            finish()
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
