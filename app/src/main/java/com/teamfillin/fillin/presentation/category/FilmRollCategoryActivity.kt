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
        filmCategoryAdapter.setItemClickListener { _, position ->
            // ToDO 서버연결 시 버튼 클릭 시 데이터 가지고 FilmCategoryImage액티비티 이동

            binding.rvCategory[position].setOnSingleClickListener {
                binding.rvCategory[position].setBackgroundResource(R.color.dark_grey_2)
                val intent = Intent(this, FilmCategoryImageActivity::class.java)
                startActivity(intent)
            }
            binding.btnBack.setOnSingleClickListener {
                finish()
            }
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
