package com.teamfillin.fillin.presentation

import android.os.Bundle
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.databinding.ActivityAddPhotoBinding

class AddPhotoActivity : BindingActivity<ActivityAddPhotoBinding>(R.layout.activity_add_photo) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnAddPhoto.setOnClickListener {
            val addPhotoDialog=CustomAddCompleteDialog(this)
            addPhotoDialog.showDialog()
        }

        binding.btnBack.setOnClickListener {
            val addCancelDialog=CustomAddCancelDialog(this)
            addCancelDialog.showDialog()
            addCancelDialog.setOnClickListener(object:CustomAddCancelDialog.OnDialogClickListener{
                override fun onClicked() {
                    //액티비티 나가기
                }
            })
        }


    }
}