package com.teamfillin.fillin.presentation

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.content.ContentUriRequestBody
import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseAddPhoto
import com.teamfillin.fillin.data.service.AddPhotoService
import com.teamfillin.fillin.databinding.ActivityAddPhotoBinding
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class AddPhotoActivity : BindingActivity<ActivityAddPhotoBinding>(R.layout.activity_add_photo) {
    @Inject
    lateinit var addPhotoService: AddPhotoService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setDialog()
        clickGallery()

    }
    private fun checkPermission() {
        val cameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
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
            type=("image/*")
            action=(Intent.ACTION_GET_CONTENT)
        }
        getResultText.launch(intent)
    }

    val getResultText = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
    { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data  //Intent를 반환->Intent에서 Uri로 get하기
            Glide.with(this).load(uri).into(binding.ivAddphoto)
            //TODO : sendAddRequest(this,uri,1,1)? 여기서 호출하는건가, 스튜디오선택/필름선택 할수있도록 수빈이코드
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

    private fun String?.toPlainRequestBody() = requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())


    fun sendAddRequest(context: Context, uri: Uri,studioId:Int,filmId:Int){
        val studioIdRequestBody:RequestBody =studioId.toString().toPlainRequestBody()
        val filmIdRequestBody: RequestBody=filmId.toString().toPlainRequestBody()
        val infoHashMap=hashMapOf<String,RequestBody>()
        infoHashMap["studioId"]=studioIdRequestBody
        infoHashMap["filmId"]=filmIdRequestBody
        val requestBody= ContentUriRequestBody(this,uri)
        val file=MultipartBody.Part.createFormData("img",requestBody.getFileName(),requestBody)

        val call: Call<BaseResponse<ResponseAddPhoto>> = addPhotoService.sendPhoto(file,infoHashMap)

        //TODO: #38 develop으로 머지되면 enqueue -> enqueueUtil 확장함수로 바꾸기
                call.enqueue(object: Callback<BaseResponse<ResponseAddPhoto>> {
            override fun onResponse(
                call: Call<BaseResponse<ResponseAddPhoto>>,
                response: Response<BaseResponse<ResponseAddPhoto>>
            ) {
                if(response.isSuccessful){
                   Toast.makeText(this@AddPhotoActivity,"업로드 완료",Toast.LENGTH_SHORT).show()

                } else{
                    Toast.makeText(this@AddPhotoActivity,"not successful",Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<BaseResponse<ResponseAddPhoto>>, t: Throwable) {
                Log.e("NetworkTest","error:$t")
            }

        })


    }

}