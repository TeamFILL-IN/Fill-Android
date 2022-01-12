package com.teamfillin.fillin.presentation.map

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
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
        binding.editSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                editTextBlankCheck()
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun setLocationListAdapter() {
        binding.rvLocationInfo.adapter = locationAdapter
        val customDecoration = CustomDecoration(1f, 10f, Color.GRAY)
        binding.rvLocationInfo.addItemDecoration(customDecoration)
        addLocationList()
    }

    private fun addLocationList() {
        binding.viewDivision.visibility = View.VISIBLE
        locationAdapter.setItem(
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
        if (binding.editSearch.text.toString().replace("", "").equals("")) {
            binding.ivClear.visibility = View.GONE
        } else {
            binding.ivClear.visibility = View.VISIBLE
        }
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