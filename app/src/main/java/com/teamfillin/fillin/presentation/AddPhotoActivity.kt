package com.teamfillin.fillin.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.content.ContentUriRequestBody
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.data.service.NewPhotoService
import com.teamfillin.fillin.databinding.ActivityAddPhotoBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class AddPhotoActivity : BindingActivity<ActivityAddPhotoBinding>(R.layout.activity_add_photo) {
    @Inject
    lateinit var service: NewPhotoService
    var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setDialog()
        clickGallery()
    }

    private fun checkPermission() {
        val cameraPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
            //프로그램 진행
            startProcess()
        } else {
            //권한요청
            requestPermission()
        }
    }

    private fun setDialog() {
        binding.btnAddPhoto.setOnClickListener {
            // TODO 서버통신 로직 추가하기
            lifecycleScope.launch {
                runCatching {
                    service.postImage(
                        body = hashMapOf(
                            "studioId" to 1.toRequestBody(),
                            "filmId" to 3.toRequestBody()
                        ),
                        file = if (uri != null) {
                            val requestBody = ContentUriRequestBody(this@AddPhotoActivity, uri!!)
                            MultipartBody.Part
                                .createFormData("image", requestBody.getFileName(), requestBody)
                        } else null
                    ).await()
                }.onSuccess {
                    toast("Nunu Success ${it.message}")
                }.onFailure {
                    toast("Nunu Failire ${it.message}")
                    Timber.e(it)
                }
            }
            val addPhotoDialog = AddCompleteDialog(this)
            addPhotoDialog.showDialog()
        }

        binding.btnBack.setOnClickListener {
            val addCancelDialog = AddCancelDialog(this)
            addCancelDialog.setOnClickListener {
                //TODO 액티비티 나가기 (develop에 모든 activity merge되면 작업)
                finish()
            }
            addCancelDialog.showDialog()

        }
    }

    private fun Int.toRequestBody() = toString().toRequestBody("text/plain".toMediaTypeOrNull())

    private fun startProcess() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        getResultText.launch(intent)
    }

    private val getResultText = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Timber.d("Nunu data ${result.data?.data}")
            uri = result.data?.data  //Intent를 반환->Intent에서 Uri로 get하기
            Glide.with(this).load(uri).into(binding.ivAddphoto)
        }
        //else if (result.resultCode == Activity.RESULT_CANCELED) {} =>Activity.RESULT_CANCELED일때 처리코드가 필요하다면
    }

    private fun requestPermission() {
        permissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
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