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
import com.teamfillin.fillin.presentation.map.CustomDecoration

class FilmRollActivity : BindingActivity<ActivityFilmRollBinding>(R.layout.activity_film_roll) {
    private lateinit var filmrollAdapter: FilmRollAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFilmRollAdapter()
        initNewPhotoRecyclerView()
    }

    private fun setFilmRollAdapter(filmRollAdapter: FilmRollAdapter) {
        val test=listOf(FilmrollInfo(R.drawable.ic_card_curation)
            ,FilmrollInfo(R.drawable.and_card_img)
            ,FilmrollInfo(R.drawable.and_card_img)
        )
        binding.rvFilmroll.apply {
            layoutManager= LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL,false)
            adapter = filmRollAdapter
        }
        filmRollAdapter.submitList(test)

    }



}