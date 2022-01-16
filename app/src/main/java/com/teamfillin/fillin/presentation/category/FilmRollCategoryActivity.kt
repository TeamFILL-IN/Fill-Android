package com.teamfillin.fillin.presentation.category

import android.graphics.Color
import android.os.Bundle
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.databinding.ActivityFilmRollCategoryBinding
import com.teamfillin.fillin.presentation.map.CustomDecoration
import com.teamfillin.fillin.presentation.map.LocationListAdapter

class FilmRollCategoryActivity :
    BindingActivity<ActivityFilmRollCategoryBinding>(R.layout.activity_film_roll_category) {
    private val categoryAdapter = CategoryListAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


}
