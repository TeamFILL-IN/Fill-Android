package com.teamfillin.fillin.presentation.map

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.databinding.ActivityMapSearchBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapSearchActivity : BindingActivity<ActivityMapSearchBinding>(R.layout.activity_map_search) {

    private val viewModel by viewModels<MapSearchViewModel>()
    private val searchListAdapter = SearchListAdapter {
        val intent = Intent().apply {
            putExtra("id", it.id)
            putExtra("name", it.name)
        }
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbarEvent()
        initView()
        initAdapter()
        initEvent()
        observe()
    }

    private fun toolbarEvent() {
        setSupportActionBar(binding.tbTitle)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    private fun initView() {
        binding.editSearch.doAfterTextChanged {
            editTextBlankCheck()
        }
        binding.editSearch.setOnEditorActionListener { _, id, _ ->
            var handled = false
            if (id == EditorInfo.IME_ACTION_SEARCH) {
                binding.ivSearch.performClick()
                handled = true
            }
            handled
        }
    }

    private fun initAdapter() {
        binding.rvLocationInfo.adapter = searchListAdapter
        val customDecoration = CustomDecoration(1f, 10f, Color.GRAY)
        binding.rvLocationInfo.addItemDecoration(customDecoration)
    }

    private fun initEvent() {
        binding.ivSearch.setOnClickListener {
            viewModel.locationSearch(binding.editSearch.text.toString())
        }

        binding.ivClear.setOnClickListener {
            binding.editSearch.text.clear()
        }
    }

    private fun observe() {
        viewModel.studioSearch.observe(this) {
            binding.rvLocationInfo.isVisible = it.isNotEmpty()
            binding.clNoResult.isVisible = it.isEmpty()
            if (it.isNotEmpty()) {
                searchListAdapter.submitList(it)
            }
        }
        viewModel.serverConnect.observe(this) {
            toast("서버 오류")
        }
    }

    private fun editTextBlankCheck() {
        binding.ivClear.isVisible = !binding.editSearch.text.isNullOrEmpty()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}