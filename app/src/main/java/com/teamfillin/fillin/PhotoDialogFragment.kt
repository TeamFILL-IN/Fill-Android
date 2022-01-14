package com.teamfillin.fillin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.teamfillin.fillin.core.fragment.stringOf
import com.teamfillin.fillin.databinding.FragmentPhotoDialogBinding

class PhotoDialogFragment : DialogFragment() {
    private lateinit var button: ImageButton
    private var _binding: FragmentPhotoDialogBinding? = null
    private val binding: FragmentPhotoDialogBinding
        get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoDialogBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_photo_dialog, container, false)
        button = view.findViewById(R.id.btn_close)

        binding.btnClose.setOnClickListener {
            dismiss()
        }
        binding.heart.setOnClickListener {
            if (binding.heart.isSelected == false) {
                binding.heart.isSelected = true
                binding.number.text = (binding.number.text.toString().toInt() + 1).toString()
            }
            else {
                binding.heart.isSelected = false
                binding.number.text = (binding.number.text.toString().toInt() - 1).toString()
            }
        }
        return _binding?.root
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