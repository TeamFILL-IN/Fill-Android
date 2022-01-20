package com.teamfillin.fillin.presentation.filmroll

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.tabs.TabLayout
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.content.receive
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.service.FilmRollService
import com.teamfillin.fillin.databinding.ActivityFilmRollBinding
import com.teamfillin.fillin.presentation.AddPhotoActivity
import com.teamfillin.fillin.presentation.category.FilmRollCategoryActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.text.FieldPosition
import javax.inject.Inject

@AndroidEntryPoint
class FilmRollActivity : BindingActivity<ActivityFilmRollBinding>(R.layout.activity_film_roll) {
    @Inject
    lateinit var service: FilmRollService
    private var filmrollAdapter = FilmRollAdapter()
    private var curationAdapter = CurationAdapter()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurationAdapter()
        setFilmRollAdapter()
        setResultFilmchoice()
        clickListener()
    }

    private fun setCurationAdapter() {
        binding.rvCuration.adapter = curationAdapter
        addCurationList()
    }

    private fun addCurationList() {
        service.getCuration().receive({
            //리사이클러뷰 어댑터 연결-서버
        }, {
            Timber.d("Error $it")
        })
    }

    private fun setFilmRollAdapter() {
        binding.rvFilmroll.adapter = filmrollAdapter
        setFilmImage()
    }

    private fun addFilmRollList(position: Int) {
        service.getFilmStyle(position).receive({
            //리사이클러뷰 어댑터 연결-서버
            }, {
                Timber.d("Error $it")
            })

    }

    private fun setFilmImage() {
        binding.filmtab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> addFilmRollList(1)
                    1 -> addFilmRollList(1)
                    2 -> addFilmRollList(3)
                    3 -> addFilmRollList(4)
                }
            }
        })

    }

    private fun setResultFilmchoice() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val film = result.data?.getStringExtra("film") ?: ""
                    binding.tvFilmchoice.text = film
                }
            }
    }

    private fun clickListener() {
        binding.fabAddPhoto.setOnSingleClickListener {
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