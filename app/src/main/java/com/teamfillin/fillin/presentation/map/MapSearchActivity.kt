package com.teamfillin.fillin.presentation.map

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.data.ResponseLocationInfo
import com.teamfillin.fillin.databinding.ActivityMapSearchBinding

class MapSearchActivity : BindingActivity<ActivityMapSearchBinding>(R.layout.activity_map_search) {
    private val locationAdapter = LocationListAdapter()
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
        binding.rvLocationInfo.adapter = locationAdapter
        val customDecoration = CustomDecoration(1f, 10f, Color.GRAY)
        binding.rvLocationInfo.addItemDecoration(customDecoration)
        addLocationList()
    }

    private fun addLocationList() {
        binding.viewDivision.visibility = View.VISIBLE
        locationAdapter.submitList(
            listOf(
                ResponseLocationInfo(
                    name = "솝트 사진관",
                    location = "서울특별시 영등포구 여의도동21-3가가가가가가가가가.."
                ),
                ResponseLocationInfo(
                    name = "현우 사진관",
                    location = "서울특별시 영등포구 여의도동21-3가가가가가가가가가.."
                ),
                ResponseLocationInfo(
                    name = "강민 사진관",
                    location = "서울특별시 영등포구 여의도동21-3가가가가가가가가가.."
                ),
                ResponseLocationInfo(
                    name = "현지 사진관",
                    location = "서울특별시 영등포구 여의도동21-3가가가가가가가가가.."
                ),
                ResponseLocationInfo(
                    name = "수빈 사진관",
                    location = "서울특별시 영등포구 여의도동21-3가가가가가가가가가.."
                )
            )
        )
    }

    private fun editTextBlankCheck() {
        binding.ivClear.isVisible = !binding.editSearch.text.isNullOrEmpty()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}