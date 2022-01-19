package com.teamfillin.fillin.presentation.map

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.data.response.ResponsePhotoReviewInfo
import com.teamfillin.fillin.data.service.StudioService
import com.teamfillin.fillin.databinding.ActivityStudioMapBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class StudioMapActivity : BindingActivity<ActivityStudioMapBinding>(R.layout.activity_studio_map),
    OnMapReadyCallback {
    @Inject
    lateinit var service: StudioService
    private lateinit var behavior: BottomSheetBehavior<NestedScrollView>
    private lateinit var fusedLocationSource: FusedLocationSource
    private var activityNaverMap: NaverMap? = null
    private val photoReviewAdapter = PhotoReviewListAdapter()
    private val hashMap = HashMap<LatLng, Int>()

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
//        addPhotoReview()
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
                activityNaverMap?.locationTrackingMode = LocationTrackingMode.None
            } else activityNaverMap?.locationTrackingMode = LocationTrackingMode.Follow
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        activityNaverMap = naverMap.apply {
            mapType = NaverMap.MapType.Navi
            setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, false)
            setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
            isNightModeEnabled = true
            locationSource = fusedLocationSource
            locationTrackingMode = LocationTrackingMode.Follow
//            addOnLocationChangeListener { location ->
//                cameraPosition =
//                    CameraPosition(LatLng(location.latitude, location.longitude), 16.0)
//            }
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
        markerLocationEvent()
        binding.btnLocation.map = naverMap
    }

    private fun markerLocationEvent() {

        lifecycleScope.launch {
            runCatching {
                service.getWholeStudio().await()
            }.onSuccess {
                it.data.studio.forEach {
                    Marker().apply {
                        position = LatLng(it.lati, it.long)
                        icon = OverlayImage.fromResource(R.drawable.ic_place_big)
                        this.map = activityNaverMap
                        hashMap[LatLng(it.lati, it.long)] = it.id
                        setOnClickListener {
                            getStudioDetail(hashMap[position])
                            getStudioPhoto(hashMap[position])
                            true
                        }
                    }
                }
            }.onFailure(Timber::e)
        }
    }

    private fun getStudioDetail(position: Int?) {
        lifecycleScope.launch {
            runCatching {
                service.getStudioDetail(position).await()
            }.onSuccess {
                val studio = it.data.studio
                binding.apply {
                    tvLocationName.text = studio.name
                    tvLocationDetail.text = studio.address
                    tvTimeDetail.text = studio.time
                    tvCallDetail.text = studio.tel
                    tvPriceDetail.text = studio.price
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
            }.onFailure(Timber::e)
        }
    }

    private fun getStudioPhoto(position: Int?) {
        lifecycleScope.launch {
            runCatching {
                service.getStudioPhoto(position).await()
            }.onSuccess {
                photoReviewAdapter.setItem(it.data.photos)
            }.onFailure(Timber::e)
        }
    }

    private val mapSearchActivityLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {

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
        super.onPause()
        binding.mapMain.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapMain.onSaveInstanceState(outState)
    }

    override fun onStop() {
        super.onStop()
        binding.mapMain.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapMain.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapMain.onLowMemory()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }
}