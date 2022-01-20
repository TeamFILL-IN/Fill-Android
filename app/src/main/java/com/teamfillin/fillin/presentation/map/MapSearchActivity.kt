package com.teamfillin.fillin.presentation.map

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.data.service.StudioService
import com.teamfillin.fillin.databinding.ActivityMapSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MapSearchActivity : BindingActivity<ActivityMapSearchBinding>(R.layout.activity_map_search) {
    @Inject
    lateinit var service: StudioService
    private val searchListAdapter = SearchListAdapter {
        val intent = Intent()
        setResult(Activity.RESULT_OK, intent)
        intent.putExtra("id", it.id)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        toolbarEvent()
        editTextIconEvent()
        setLocationListAdapter()

    }

    private fun toolbarEvent() {
        setSupportActionBar(binding.tbTitle)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
            setHomeAsUpIndicator(R.drawable.ic_back)
        }
    }

    private fun editTextIconEvent() {
        binding.editSearch.doAfterTextChanged {
            editTextBlankCheck()
        }
    }

    private fun setLocationListAdapter() {
        binding.rvLocationInfo.adapter = searchListAdapter
        val customDecoration = CustomDecoration(1f, 10f, Color.GRAY)
        binding.rvLocationInfo.addItemDecoration(customDecoration)
        locationSearchEvent()
    }

    private fun locationSearchEvent() {
        binding.ivSearch.setOnClickListener {
            lifecycleScope.launch {
                runCatching {
                    service.getSearchInfo(binding.editSearch.text.toString()).await()
                }.onSuccess {
                    binding.rvLocationInfo.isVisible = it.data.studios.isNotEmpty()
                    binding.clNoResult.isVisible = it.data.studios.isEmpty()
                    if (it.data.studios.isNotEmpty()) {
                        searchListAdapter.submitList(it.data.studios)
                    }
                }.onFailure(Timber::e)
            }
        }

        binding.ivClear.setOnClickListener {
            binding.editSearch.text.clear()
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