package com.teamfillin.fillin.presentation.my.terms

import android.os.Bundle
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.databinding.ActivityTermsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TermsActivity: BindingActivity<ActivityTermsBinding>(R.layout.activity_terms) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initEvent()
    }

    private fun initEvent() {
        binding.btnBackHome.setOnClickListener {
            finish()
        }
    }
}