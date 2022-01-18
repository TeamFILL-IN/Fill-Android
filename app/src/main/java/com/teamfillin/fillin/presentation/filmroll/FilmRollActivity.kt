package com.teamfillin.fillin.presentation.filmroll

import android.content.Intent
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
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.CategoryInfo
import com.teamfillin.fillin.databinding.ActivityFilmRollBinding
import com.teamfillin.fillin.presentation.AddPhotoActivity
import com.teamfillin.fillin.presentation.category.FilmCategoryAdapter
import com.teamfillin.fillin.presentation.category.FilmRollCategoryActivity
import com.teamfillin.fillin.presentation.map.CustomDecoration

class FilmRollActivity : BindingActivity<ActivityFilmRollBinding>(R.layout.activity_film_roll) {
    private var filmrollAdapter = FilmRollAdapter()
    private var curationAdapter = CurationAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFilmRollAdapter()
        setCurationAdapter()
        clickListener()
    }

    private fun setCurationAdapter() {
        binding.rvCuration.adapter = curationAdapter
        addCurationList()
    }

    private fun addCurationList() {
        curationAdapter.submitList(
            listOf(
                CurationInfo(
                    R.drawable.ic_card_curation, CURATION_INFO_TYPE
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE
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
    private fun clickListener(){
        binding.fabAddPhoto.setOnSingleClickListener{
            val intent = Intent(this, AddPhotoActivity::class.java)
            startActivity(intent)
        }
        binding.tvFilmchoice.setOnSingleClickListener {
            val intent = Intent(this, FilmRollCategoryActivity::class.java)
            startActivity(intent)
        }
        binding.btnBack.setOnSingleClickListener {
            finish()
        }
    }

}