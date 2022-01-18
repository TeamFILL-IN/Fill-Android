package com.teamfillin.fillin.presentation.filmroll

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.teamfillin.fillin.R
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NEW_PHOTOS_TYPE
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NEXT_BUTTON_TYPE
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NewPhotosAdapter
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NewPhotosData
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.data.CategoryInfo
import com.teamfillin.fillin.databinding.ActivityFilmRollBinding
import com.teamfillin.fillin.presentation.category.FilmCategoryAdapter
import com.teamfillin.fillin.presentation.map.CustomDecoration

class FilmRollActivity : BindingActivity<ActivityFilmRollBinding>(R.layout.activity_film_roll) {
    private var filmrollAdapter = FilmRollAdapter()
    private var curationAdapter = CurationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFilmRollAdapter()
        setCurationAdapter()
    }

    private fun setCurationAdapter() {
        binding.rvCuration.adapter = curationAdapter
        addCurationList()
    }


    private fun addCurationList() {
        curationAdapter.submitList(
            listOf(
                CurationInfo(
                    R.drawable.ic_card_curation
                ),
                CurationInfo(
                    R.drawable.and_photo_rectangle
                ),
                CurationInfo(
                    R.drawable.and_photo_rectangle
                ),
                CurationInfo(
                    R.drawable.and_photo_rectangle
                ),
                CurationInfo(
                    R.drawable.and_photo_rectangle
                ),
                CurationInfo(
                    R.drawable.and_photo_rectangle
                )
            )
        )
    }

    private fun setFilmRollAdapter() {
        binding.rvFilmroll.adapter = filmrollAdapter
        addFilmRollList()
    }

    private fun addFilmRollList() {
        filmrollAdapter.submitList(
            listOf(
                FilmrollInfo(
                    R.drawable.and_photo_rectangle
                ),
                FilmrollInfo(
                    R.drawable.ic_card_curation
                ),
                FilmrollInfo(
                    R.drawable.and_photo_rectangle
                ),
                FilmrollInfo(
                    R.drawable.ic_card_curation
                ),
                FilmrollInfo(
                    R.drawable.ic_card_curation
                ),
                FilmrollInfo(
                    R.drawable.and_photo_rectangle
                )
            )
        )
    }


}