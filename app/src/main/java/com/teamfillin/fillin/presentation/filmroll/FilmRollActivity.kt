package com.teamfillin.fillin.presentation.filmroll

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import com.google.android.material.tabs.TabLayout
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.content.receive
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.service.FilmRollService
import com.teamfillin.fillin.databinding.ActivityFilmRollBinding
import com.teamfillin.fillin.presentation.filmroll.add.AddPhotoActivity
import com.teamfillin.fillin.presentation.category.FilmRollCategoryActivity
import com.teamfillin.fillin.presentation.dialog.PhotoDialogFragment
import com.teamfillin.fillin.presentation.filmroll.add.AddCompleteDialog
import com.teamfillin.fillin.presentation.map.SpaceDecoration
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class FilmRollActivity : BindingActivity<ActivityFilmRollBinding>(R.layout.activity_film_roll) {
    @Inject
    lateinit var service: FilmRollService
    private val viewModel by viewModels<FilmRollViewModel>()
    private val filmRollPagingAdapter = FilmRollPagingAdapter {
        val dialog = PhotoDialogFragment()
        val bundle = Bundle().apply { putString("photoUrl", it.imageUrl) }
        dialog.apply {
            arguments = bundle
            show(supportFragmentManager, "dialog")
        }
    }
    private lateinit var curationAdapter: CurationAdapter
    private val addPhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                AddCompleteDialog(this).showDialog()
            }
        }
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        clickListener()
        setCurationAdapter()
        setFilmRollAdapter()
        setResultFilmchoice()
        lifecycleScope.launch {
            viewModel.getCategoryFilm(1, -1)
                .flowWithLifecycle(lifecycle)
                .collectLatest {
                    Timber.d("Nunu filmId paging: $it")
                    filmRollPagingAdapter.submitData(it)
                }
        }
    }

    private fun setCurationAdapter() {
        addCurationList()
    }

    private fun addCurationList() {
        service.getCuration().receive({
            curationAdapter = CurationAdapter(it.data.curation) {
                val dialog = PhotoDialogFragment()
                val bundle = Bundle().apply { putString("photoUrl", it.imageUrl) }
                dialog.apply {
                    arguments = bundle
                    show(supportFragmentManager, "dialog")
                }
            }
            binding.rvCuration.adapter = curationAdapter
            curationAdapter.submitList(it.data.photos)
        }, {
            Timber.d("Error $it")
        })
    }

    private fun setFilmRollAdapter() {
        binding.rvFilmroll.adapter = filmRollPagingAdapter
        binding.rvFilmroll.addItemDecoration(SpaceDecoration(12))
        binding.filmtab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab!!.position) {
                    0 -> retrieveCategoryPhotoList(1, -1)
                    1 -> retrieveCategoryPhotoList(2, -1)
                    2 -> retrieveCategoryPhotoList(3, -1)
                    3 -> retrieveCategoryPhotoList(4, -1)
                }
            }
        })
    }

    private fun retrieveCategoryPhotoList(tabPosition: Int, filmId: Int) {
        if (filmId == -1) {
            Timber.d("Nunu tabPosition: $tabPosition")
            binding.tvFilmchoice.text = "필름 종류를 선택하세요"
            lifecycleScope.launch {
                filmRollPagingAdapter.submitData(PagingData.empty())
                viewModel.getCategoryFilm(tabPosition, -1)
                    .flowWithLifecycle(lifecycle)
                    .collectLatest {
                        Timber.d("Nunu tabPosition paging: $it")
                        filmRollPagingAdapter.submitData(it)
                    }
            }
        } else {
            Timber.d("Nunu filmId: $filmId")
            lifecycleScope.launch {
                filmRollPagingAdapter.submitData(PagingData.empty())
                viewModel.getCategoryFilm(-1, filmId)
                    .flowWithLifecycle(lifecycle)
                    .collectLatest {
                        Timber.d("Nunu filmId paging: $it")
                        filmRollPagingAdapter.submitData(it)
                    }
            }
        }
    }

    private fun setResultFilmchoice() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val film = result.data?.getStringExtra("film") ?: ""
                    binding.tvFilmchoice.text = film
                    val id = result.data?.getIntExtra("id", -1) ?: -1
                    retrieveCategoryPhotoList(-1, id)
                }
            }
    }

    private fun clickListener() {
        binding.fabAddPhoto.setOnSingleClickListener {
            val intent = Intent(this, AddPhotoActivity::class.java)
            addPhotoLauncher.launch(intent)
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