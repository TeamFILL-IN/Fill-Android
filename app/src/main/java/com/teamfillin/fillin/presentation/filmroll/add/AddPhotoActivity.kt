package com.teamfillin.fillin.presentation.filmroll.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.content.ContentUriRequestBody
import com.teamfillin.fillin.core.context.colorOf
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.databinding.ActivityAddPhotoBinding
import com.teamfillin.fillin.presentation.category.FilmRollCategoryActivity
import com.teamfillin.fillin.presentation.map.MapSearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class AddPhotoActivity : BindingActivity<ActivityAddPhotoBinding>(R.layout.activity_add_photo) {
    //    @Inject
//    lateinit var service: FilmRollService
//    var uri: Uri? = null
    private lateinit var filmSelectLauncher: ActivityResultLauncher<Intent>
    private lateinit var studioSelectLauncher: ActivityResultLauncher<Intent>
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private val viewModel by viewModels<AddPhotoViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initResultLauncher()
        initView()
    }

    private fun initView() {
        binding.btnAddPhoto.setOnClickListener {
            viewModel.registerPhoto()
        }

        binding.tvFilm.setOnSingleClickListener {
            filmSelectLauncher.launch(Intent(this, FilmRollCategoryActivity::class.java))
        }

        binding.tvStudio.setOnSingleClickListener {
            studioSelectLauncher.launch(Intent(this, MapSearchActivity::class.java))
        }

        binding.btnBack.setOnClickListener {
            val addCancelDialog = AddCancelDialog(this)
            addCancelDialog.setOnClickListener { finish() }
            addCancelDialog.showDialog()
        }

        binding.ivAddphoto.setOnClickListener {
            checkPermission()
        }

        lifecycleScope.launch {
            viewModel.isClickable.flowWithLifecycle(lifecycle)
                .collect {
                    with(binding.btnAddPhoto) {
                        isClickable = it
                        setBackgroundColor(
                            if (it) colorOf(R.color.fillin_red) else colorOf(R.color.grey_3)
                        )
                        setTextColor(
                            if (it) colorOf(R.color.fillin_black) else Color.parseColor("#6F6F6F")
                        )
                    }
                }
        }

        lifecycleScope.launch {
            viewModel.registerSuccess.flowWithLifecycle(lifecycle)
                .collect {
                    setResult(Activity.RESULT_OK)
                    finish()
                }
        }
    }

    private fun initResultLauncher() {
        filmSelectLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val film = result.data?.getStringExtra("film") ?: ""
                    val styleId = result.data?.getIntExtra("id", 0) ?: 0
                    binding.tvFilm.text = film
                    viewModel.setFilmMetaData(styleId, film)
                }
            }
        studioSelectLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    val locationId = it.data?.getIntExtra("id", 0) ?: 0
                    val studioName = it.data?.getStringExtra("name") ?: ""
                    viewModel.setStudio(locationId, studioName)
                    binding.tvStudio.text = studioName
                }
            }
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                when (isGranted) {
                    true -> startImagePicker()
                    false -> Toast.makeText(this, "갤러리 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                }
            }
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                Timber.d("Nunu data ${result.data?.data}")
                val uri = result.data?.data  //Intent를 반환->Intent에서 Uri로 get하기
                Glide.with(this).load(uri).into(binding.ivAddphoto)
                if (uri != null) {
                    val requestBody = ContentUriRequestBody(this@AddPhotoActivity, uri)
                    viewModel.setUri(requestBody)
                }
            }
        }
    }

    private fun checkPermission() {
        val cameraPermission =
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (cameraPermission == PackageManager.PERMISSION_GRANTED) {
            //프로그램 진행
            startImagePicker()
        } else {
            //권한요청
            requestPermission()
        }
    }

    override fun onBackPressed() {
        AddCancelDialog(this).apply {
            setOnClickListener { super.onBackPressed() }
        }.showDialog()
    }

    private fun startImagePicker() {
        val intent = Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }
        imagePickerLauncher.launch(intent)
    }


    private fun requestPermission() {
        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
    }
}