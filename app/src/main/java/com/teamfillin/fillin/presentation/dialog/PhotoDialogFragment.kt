package com.teamfillin.fillin.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.teamfillin.fillin.databinding.FragmentPhotoDialogBinding

class PhotoDialogFragment : DialogFragment() {
    private var _binding: FragmentPhotoDialogBinding? = null
    private val binding: FragmentPhotoDialogBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoDialogBinding.inflate(layoutInflater, container, false)

        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.heart.setOnClickListener {
            binding.number.text = (
                    binding.number.text.toString()
                        .toInt() + (if (binding.heart.isSelected == false) 1 else -1)
                    ).toString()
            binding.heart.isSelected = !binding.heart.isSelected
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val photoUrl = arguments?.getString("photoUrl")
        Glide.with(requireActivity()).load(photoUrl).into(binding.ivPhoto)
    }

    //휴대폰 크기 맞춰 자동 조절 다이얼로그
    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}