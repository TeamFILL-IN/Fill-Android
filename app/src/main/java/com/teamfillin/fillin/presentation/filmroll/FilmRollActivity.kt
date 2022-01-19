package com.teamfillin.fillin.presentation.filmroll

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.databinding.ActivityFilmRollBinding
import com.teamfillin.fillin.presentation.add.AddPhotoActivity
import com.teamfillin.fillin.presentation.category.FilmRollCategoryActivity

class FilmRollActivity : BindingActivity<ActivityFilmRollBinding>(R.layout.activity_film_roll) {
    private var filmrollAdapter = FilmRollAdapter()
    private var curationAdapter = CurationAdapter()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFilmRollAdapter()
        setCurationAdapter()
        setResultFilmchoice()
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
                    R.drawable.ic_curation_cover, CURATION_INFO_TYPE,"따뜻한 사진을 \n 원한다면"
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE,""
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE,""
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE,""
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE,""
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE,""
                ),
                CurationInfo(
                    R.drawable.and_card_img, CURATION_TYPE,""
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

    private fun setResultFilmchoice(){
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK){
                val film = result.data?.getStringExtra("film") ?: ""
                binding.tvFilmchoice.text = film
            }
        }
    }
    private fun clickListener(){
        binding.fabAddPhoto.setOnSingleClickListener{
            val intent = Intent(this, AddPhotoActivity::class.java)
            startActivity(intent)
        }
        binding.tvFilmchoice.setOnSingleClickListener {
            val intent = Intent(this, FilmRollCategoryActivity::class.java)
            resultLauncher.launch(intent)
        }
        binding.btnBack.setOnSingleClickListener {
            finish()
        }
    }

}