package com.teamfillin.fillin.presentation.map

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.NestedScrollView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.content.EventObserver
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.databinding.ActivityStudioMapBinding
import com.teamfillin.fillin.presentation.dialog.PhotoDialogFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class StudioMapActivity : BindingActivity<ActivityStudioMapBinding>(R.layout.activity_studio_map),
    OnMapReadyCallback {

    private val viewModel by viewModels<StudioMapViewModel>()
    private lateinit var behavior: BottomSheetBehavior<NestedScrollView>
    private lateinit var fusedLocationSource: FusedLocationSource
    private var naverMap: NaverMap? = null
    private val photoReviewAdapter = PhotoReviewListAdapter {
        val dialog =
            PhotoDialogFragment.newInstance(it.imageUrl, it.userImageUrl, it.filmName, it.nickname)
        dialog.show(supportFragmentManager, "dialog")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mapMain.onCreate(savedInstanceState)
        behavior = BottomSheetBehavior.from(binding.clBottomSheet)
        fusedLocationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        binding.mapMain.getMapAsync(this)
        behavior.state = BottomSheetBehavior.STATE_HIDDEN
        toolbarEvent()
        searchClickEvent()
        initAdapter()
        observe()
    }

    private fun toolbarEvent() {
        setSupportActionBar(binding.tbTitle)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun searchClickEvent() {
        binding.clSearch.setOnClickListener {
            val intent = Intent(this, MapSearchActivity::class.java)
            mapSearchActivityLauncher.launch(intent)
        }
    }

    private fun initAdapter() {
        binding.rvPhotoReview.adapter = photoReviewAdapter
        val customDecoration = SpaceDecoration(7)
        binding.rvPhotoReview.addItemDecoration(customDecoration)
    }

    private fun observe() {
        viewModel.location.observe(this) {
            it.map { location ->
                Marker().apply {
                    position = location
                    icon = OverlayImage.fromResource(R.drawable.ic_place_select)
                    this.map = naverMap
                }
            }.forEach { marker ->
                marker.setOnClickListener {
                    viewModel.studioIdHash[marker.position]?.let { id ->
                        viewModel.studioDetail(id)
                        viewModel.studioPhoto(id)
                    }
                    true
                }
            }
        }

        viewModel.studio.observe(this) {
            binding.apply {
                tvLocationName.text = it.name
                tvLocationDetail.text = it.address
                tvTimeDetail.text = it.time
                tvCallDetail.text = it.tel
                tvPriceDetail.text = it.price
            }
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED

            binding.tvLink.isVisible = it.site?.isNotEmpty() ?: false
            it.site?.let { site -> linkText(site) }
        }

        viewModel.photos.observe(this) {
            photoReviewAdapter.submitList(it)
        }

        viewModel.cameraZoom.observe(this, EventObserver {
            val cameraUpdate = CameraUpdate.scrollTo(viewModel.locationHash[it]!!)
            naverMap?.moveCamera(cameraUpdate)
        })

        viewModel.serverConnect.observe(this) {
            toast("서버 통신 오류")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (fusedLocationSource.onRequestPermissionsResult(
                requestCode,
                permissions,
                grantResults
            )
        ) {
            if (!fusedLocationSource.isActivated) {
                naverMap?.locationTrackingMode = LocationTrackingMode.None
            } else naverMap?.locationTrackingMode = LocationTrackingMode.Follow
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap.apply {
            mapType = NaverMap.MapType.Navi
            setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, false)
            setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
            isNightModeEnabled = true
            locationSource = fusedLocationSource
            locationTrackingMode = LocationTrackingMode.Follow
            minZoom = 6.0
            maxZoom = 18.0
            uiSettings.run {
                isCompassEnabled = false
                isScaleBarEnabled = false
                isZoomControlEnabled = false
                isLocationButtonEnabled = false
            }
        }

        binding.clBottomSheet.visibility = View.VISIBLE
        viewModel.studioLocation()
        binding.btnLocation.map = naverMap
        this.naverMap?.setOnMapClickListener { _, _ ->
            behavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun linkText(site: String) {
        val spannable = SpannableStringBuilder("웹 사이트로 이동")
        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(p0: View) {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(site))
                startActivity(intent)
            }
        }, 0, 9, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        binding.tvLink.text = spannable
        binding.tvLink.movementMethod = LinkMovementMethod.getInstance()
    }

    private val mapSearchActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            val locationId = it.data?.getIntExtra("id", 0)
            locationId?.let {
                viewModel.studioDetail(it)
                viewModel.studioPhoto(it)
            }
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

    override fun onStart() {
        super.onStart()
        binding.mapMain.onStart()
    }

    override fun onResume() {
        super.onResume()
        binding.mapMain.onResume()
    }

    override fun onPause() {
        binding.mapMain.onPause()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapMain.onSaveInstanceState(outState)
    }

    override fun onStop() {
        binding.mapMain.onStop()
        super.onStop()
    }

    override fun onDestroy() {
        binding.mapMain.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        binding.mapMain.onLowMemory()
        super.onLowMemory()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}