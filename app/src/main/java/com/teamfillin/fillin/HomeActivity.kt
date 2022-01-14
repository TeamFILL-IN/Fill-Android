package com.teamfillin.fillin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.LinearLayout.HORIZONTAL
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
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
        setContentView(binding.root)
    }


    private fun initDatas() {
        val exphotos = R.drawable.and_photo_rectangle
        newPhotosData = listOf(

            NewPhotosData(exphotos),
            NewPhotosData(exphotos),
            NewPhotosData(exphotos),
            NewPhotosData(exphotos),
            NewPhotosData(exphotos),
            NewPhotosData(exphotos),
            NewPhotosData(exphotos),
            NewPhotosData(exphotos)
        )

    }

    @SuppressLint("WrongConstant")
    private fun initNewPhotoRecyclerView() {
        newPhotosAdapter = NewPhotosAdapter()
        newPhotosAdapter.replaceList(newPhotosData)
        binding.rvNewPhotos.adapter = newPhotosAdapter
        val linearlayoutManager = LinearLayoutManager(this, HORIZONTAL, false)
        binding.rvNewPhotos.layoutManager = linearlayoutManager
    }

    private fun popup() {
        binding.apply {
            btnClose.setOnClickListener {
                if (clPopup.isVisible)
                    clPopup.isVisible = false

            }
            tvNotice.setOnClickListener {
                toast("현상소 제보 Page이동")
                var dialog = PhotoDialogFragment()
                dialog.show(supportFragmentManager, "dialogfragmnet")
            }
        }
    }
}


