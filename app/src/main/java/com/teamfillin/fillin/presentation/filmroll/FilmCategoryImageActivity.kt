package com.teamfillin.fillin.presentation.filmroll

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.databinding.ActivityCategoryImageBinding
import com.teamfillin.fillin.presentation.filmroll.add.AddPhotoActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilmCategoryImageActivity :
    BindingActivity<ActivityCategoryImageBinding>(R.layout.activity_category_image) {

    private val viewModel by viewModels<FilmCategoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // click()
        val filmImageAdapter = FilmImageAdapter()
        binding.rvCategoryImage.adapter = filmImageAdapter
        lifecycleScope.launch {
            viewModel.photos.flowWithLifecycle(lifecycle)
                .collect(filmImageAdapter::submitData)
        }
    }

    private fun click() {
        binding.fabAddPhoto.setOnSingleClickListener {
            val intent = Intent(this, AddPhotoActivity::class.java)
            startActivity(intent)
        }
        binding.btnBack.setOnSingleClickListener { finish() }
    }
}
