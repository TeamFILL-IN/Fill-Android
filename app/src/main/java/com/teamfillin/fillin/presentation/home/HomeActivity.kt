package com.teamfillin.fillin.presentation.home

import android.os.Bundle
import androidx.core.view.isVisible
import com.teamfillin.fillin.presentation.dialog.PhotoDialogFragment
import com.teamfillin.fillin.R
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NEW_PHOTOS_TYPE
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NEXT_BUTTON_TYPE
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NewPhotosAdapter
import com.teamfillin.fillin.com.teamfillin.fillin.presentation.home.NewPhotosData
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.databinding.ActivityHomeBinding

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private lateinit var newPhotosAdapter: NewPhotosAdapter
    var newPhotosData = listOf<NewPhotosData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDatas()
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
            NewPhotosData(R.drawable.and_photo_rectangle, NEXT_BUTTON_TYPE)
        )
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
}


