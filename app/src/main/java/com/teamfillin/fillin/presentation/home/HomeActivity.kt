package com.teamfillin.fillin.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import com.teamfillin.fillin.presentation.dialog.PhotoDialogFragment
import com.teamfillin.fillin.R
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NEW_PHOTOS_TYPE
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NEXT_BUTTON_TYPE
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NewPhotosAdapter
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.databinding.ActivityHomeBinding
import com.teamfillin.fillin.presentation.AddPhotoActivity
import com.teamfillin.fillin.presentation.MyPageActivity
import com.teamfillin.fillin.presentation.filmroll.FilmRollActivity
import com.teamfillin.fillin.presentation.map.MapSearchActivity
import com.teamfillin.fillin.presentation.map.StudioMapActivity
import com.teamfillin.fillin.presentation.home.NewPhotosData

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private lateinit var newPhotosAdapter: NewPhotosAdapter
    var newPhotosData = listOf<NewPhotosData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDatas()
        clickListener()
        initNewPhotoRecyclerView()
        popup()
    }

    private fun initDatas() {
        newPhotosData = listOf(
            NewPhotosData(R.drawable.and_photo_rectangle, NEW_PHOTOS_TYPE),
            NewPhotosData(R.drawable.and_photo_rectangle, NEW_PHOTOS_TYPE),
            NewPhotosData(R.drawable.and_photo_rectangle, NEW_PHOTOS_TYPE),
            NewPhotosData(R.drawable.and_photo_rectangle, NEW_PHOTOS_TYPE),
            NewPhotosData(R.drawable.and_photo_rectangle, NEW_PHOTOS_TYPE),
            NewPhotosData(R.drawable.and_photo_rectangle, NEW_PHOTOS_TYPE),
            NewPhotosData(R.drawable.and_photo_rectangle, NEW_PHOTOS_TYPE),
            NewPhotosData(R.drawable.and_photo_rectangle, NEW_PHOTOS_TYPE),
            NewPhotosData(R.layout.item_next_button, NEXT_BUTTON_TYPE)
        )
    }

    private fun clickListener() {
        binding.btnAddphoto.setOnSingleClickListener {
            val intent = Intent(this, AddPhotoActivity::class.java)
            startActivity(intent)
        }
        binding.btnFilmroll.setOnSingleClickListener {
            val intent = Intent(this, FilmRollActivity::class.java)
            startActivity(intent)
        }
        binding.btnStudiomap.setOnSingleClickListener {
            val intent = Intent(this, StudioMapActivity::class.java)
            startActivity(intent)
        }
        binding.btnMypage.setOnSingleClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initNewPhotoRecyclerView() {
        newPhotosAdapter = NewPhotosAdapter()
        newPhotosAdapter.replaceList(newPhotosData)
        binding.rvNewPhotos.adapter = newPhotosAdapter
    }

    private fun popup() {
        binding.apply {
            btnClose.setOnClickListener {
                clPopup.isVisible = !clPopup.isVisible
            }
            tvNotice.setOnClickListener {
                toast("현상소 제보 Page이동")
                val dialog = PhotoDialogFragment()
                dialog.show(supportFragmentManager, "dialogfragmnet")
            }
        }
    }

    companion object {
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, HomeActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
}


