package com.teamfillin.fillin.presentation.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.intent.stringArgs
import com.teamfillin.fillin.databinding.FragmentPhotoDialogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotoDialogFragment : DialogFragment() {
    private var _binding: FragmentPhotoDialogBinding? = null
    private val binding: FragmentPhotoDialogBinding
        get() = requireNotNull(_binding)
    private val photoUrl by stringArgs()
    private val profileUrl by stringArgs()
    private val filmName by stringArgs()
    private val userName by stringArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoDialogBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO by Nunu 좋아요 기능 원복
//        binding.heart.setOnClickListener {
//            binding.number.text = (
//                    binding.number.text.toString()
//                        .toInt() + (if (!binding.heart.isSelected) 1 else -1)
//                    ).toString()
//            binding.heart.isSelected = !binding.heart.isSelected
//        }
        Glide.with(requireContext())
            .load(photoUrl)
            .into(binding.ivPhoto)
        Glide.with(requireContext())
            .load(profileUrl)
            .circleCrop()
            .placeholder(R.drawable.ic_basic_profile)
            .into(binding.ivProfile)
        with(binding) {
            btnClose.setOnClickListener { dismiss() }
            tvName.text = userName
            tvFilmname.text = filmName
        }
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

    companion object {
        @JvmStatic
        fun newInstance(photoUrl: String, profileUrl: String, filmName: String, userName: String) =
            PhotoDialogFragment().apply {
                arguments = Bundle().apply {
                    putString("photoUrl", photoUrl)
                    putString("profileUrl", profileUrl)
                    putString("filmName", filmName)
                    putString("userName", userName)
                }
            }
    }
}