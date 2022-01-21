package com.teamfillin.fillin.presentation.category

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.google.android.material.tabs.TabLayout
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.data.service.FilmStyleService
import com.teamfillin.fillin.databinding.ActivityFilmRollCategoryBinding
import com.teamfillin.fillin.presentation.map.CustomDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class FilmRollCategoryActivity :
    BindingActivity<ActivityFilmRollCategoryBinding>(R.layout.activity_film_roll_category) {

    @Inject
    lateinit var service: FilmStyleService
    private val filmCategoryAdapter = FilmCategoryAdapter {
        val intent = Intent().apply {
            putExtra("film", it.name)
            putExtra("StyleId", it.styleId)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFilmCategoryAdapter()
        tabSelectEvent()
    }

    private fun setFilmCategoryAdapter() {
        binding.rvCategory.adapter = filmCategoryAdapter
        val customDecoration = CustomDecoration(2f, 10f, Color.DKGRAY)
        binding.rvCategory.addItemDecoration(customDecoration)
        getFilmCategoryList(1)
    }

    private fun tabSelectEvent() {
        binding.filmtab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                Timber.d("kangmin")
                when (tab!!.position) {
                    0 -> getFilmCategoryList(1)
                    1 -> getFilmCategoryList(2)
                    2 -> getFilmCategoryList(3)
                    else -> getFilmCategoryList(4)
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        }
        )
    }

    private fun getFilmCategoryList(styleId: Int) {
        lifecycleScope.launch {
            runCatching {
                service.getFilmType(styleId).await()
            }.onSuccess {
                filmCategoryAdapter.submitList(it.data.films)
            }.onFailure(Timber::e)
        }
    }
}