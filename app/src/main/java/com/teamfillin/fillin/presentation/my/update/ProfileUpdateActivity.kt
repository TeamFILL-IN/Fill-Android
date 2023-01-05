package com.teamfillin.fillin.presentation.my.update

import android.content.Intent
import android.graphics.Color
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.context.colorOf
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.core.view.UiState
import com.teamfillin.fillin.databinding.ActivityProfileUpdateBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.io.IOException

@AndroidEntryPoint
class ProfileUpdateActivity :
    BindingActivity<ActivityProfileUpdateBinding>(R.layout.activity_profile_update) {
    private val viewModel by viewModels<ProfileUpdateViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.viewModel = viewModel
        initView()
        initEvent()
        observe()
    }

    private fun initView() {
        binding.etNickname.doAfterTextChanged {
            editTextNameBlankCheck()
        }

        binding.etCamera.doAfterTextChanged {
            editTextCameraBlankCheck()
        }
    }

    private fun initEvent() {
        binding.ivNicknameClear.setOnClickListener {
            viewModel.nickname.value = ""
        }

        binding.ivCameraClear.setOnClickListener {
            viewModel.cameraName.value = ""
        }

        binding.ivProfile.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            filterActivityLauncher.launch(intent)
        }

        binding.btnAddPhoto.setOnClickListener {
            toast("asdf")
            viewModel.putUser()
        }
    }

    private fun observe() {
        viewModel.isClickable.flowWithLifecycle(lifecycle)
            .onEach {
                with(binding.btnAddPhoto) {
                    isClickable = it
                    setBackgroundColor(
                        if (it) colorOf(R.color.fillin_red) else Color.parseColor("#474645")
                    )
                    setTextColor(
                        if (it) colorOf(R.color.fillin_black) else Color.parseColor("#6F6F6F")
                    )
                }
            }.launchIn(lifecycleScope)

        viewModel.updateUser.flowWithLifecycle(lifecycle)
            .onEach {
                when (it) {
                    is UiState.Success -> {
                        toast("프로필 수정이 완료되었습니다.")
                        finish()
                    }
                    is UiState.Failure -> {
                        toast(it.msg)
                    }
                    else -> {}
                }
            }
    }

    private fun editTextNameBlankCheck() {
        binding.ivNicknameClear.isVisible = !binding.etNickname.text.isNullOrEmpty()
    }

    private fun editTextCameraBlankCheck() {
        binding.ivCameraClear.isVisible = !binding.etCamera.text.isNullOrEmpty()
    }

    private val filterActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK && it.data != null) {
                val imageUri = it.data?.data
                try {
                    imageUri?.let {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                            viewModel.setImageBitmap(ImageDecoder.decodeBitmap(ImageDecoder.createSource(contentResolver, imageUri)))
                        } else {
                            viewModel.setImageBitmap(MediaStore.Images.Media.getBitmap(contentResolver, imageUri))
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                Glide.with(this).load(imageUri).circleCrop().into(binding.ivProfile)
            } else if (it.resultCode == RESULT_CANCELED) {
                toast("사진 선택 취소")
            } else {
                Log.d("ActivityResult", "something wrong")
            }
        }
}