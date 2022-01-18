package com.teamfillin.fillin.presentation.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.databinding.ActivityAddPhotoBinding

class AddPhotoActivity : BindingActivity<ActivityAddPhotoBinding>(R.layout.activity_add_photo) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setDialog()
        clickGallery()

    }
    private fun checkPermission() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
            //프로그램 진행
            startProcess()
        } else {
            //권한요청
            requestPermission()
        }
    }

    private fun setDialog(){
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

    private fun startProcess() {
        val intent = Intent().apply {
            setType("image/*")
            setAction(Intent.ACTION_GET_CONTENT)
        }
        getResultText.launch(intent)
    }

    val getResultText = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data  //Intent를 반환->Intent에서 Uri로 get하기
            Glide.with(this).load(uri).into(binding.ivAddphoto)
        }
        //else if (result.resultCode == Activity.RESULT_CANCELED) {} =>Activity.RESULT_CANCELED일때 처리코드가 필요하다면
    }

    private fun requestPermission() {
        permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission())
    { isGranted: Boolean ->
        when (isGranted) {
            true -> startProcess()
            false -> Toast.makeText(this, "갤러리 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clickGallery() {
        binding.ivAddphoto.setOnClickListener {
            checkPermission()
        }
    }

}