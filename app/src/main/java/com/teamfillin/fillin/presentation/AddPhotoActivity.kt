package com.teamfillin.fillin.presentation

import android.os.Bundle
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.databinding.ActivityAddPhotoBinding

class AddPhotoActivity : BindingActivity<ActivityAddPhotoBinding>(R.layout.activity_add_photo) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnAddPhoto.setOnClickListener {
            val addPhotoDialog = AddCompleteDialog(this)
            addPhotoDialog.showDialog()
        }

        binding.btnBack.setOnClickListener {
            val addCancelDialog = AddCancelDialog(this)
            addCancelDialog.showDialog()
            addCancelDialog.setOnClickListener {
                //TODO 액티비티 나가기 (develop에 모든 activity merge되면 작업)
            }
        }
    }
}