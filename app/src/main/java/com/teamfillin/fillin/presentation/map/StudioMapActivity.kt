package com.teamfillin.fillin.presentation.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource
import com.teamfillin.fillin.databinding.ActivityStudioMapBinding


class StudioMapActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStudioMapBinding
    private lateinit var locationSource: FusedLocationSource
    private var naverMap: NaverMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudioMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.mapMain.onCreate(savedInstanceState)
        locationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        binding.mapMain.getMapAsync(NaverMapProvider(locationSource, naverMap, binding))
        toolbarEvent()
        searchClickEvent()

    }

    private fun toolbarEvent() {
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)
        ab.setDisplayShowTitleEnabled(false)
    }

    private fun searchClickEvent() {
        binding.layoutSearch.setOnClickListener {

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
            if (!locationSource.isActivated) {
                naverMap?.locationTrackingMode = LocationTrackingMode.None
            } else naverMap?.locationTrackingMode = LocationTrackingMode.Follow
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private class NaverMapProvider(
        private val trackingLocationSource: LocationSource,
        private var activityNaverMap: NaverMap? = null,
        private var binding: ActivityStudioMapBinding
    ) : OnMapReadyCallback {
        override fun onMapReady(naverMap: NaverMap) {
            activityNaverMap = naverMap.apply {
                mapType = NaverMap.MapType.Navi
                setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, false)
                setLayerGroupEnabled(NaverMap.LAYER_GROUP_BUILDING, true)
                isNightModeEnabled = true
                locationSource = trackingLocationSource
                locationTrackingMode = LocationTrackingMode.Follow
                addOnLocationChangeListener { location ->
                    cameraPosition =
                        CameraPosition(LatLng(location.latitude, location.longitude), 16.0)
                }
                uiSettings.run {
                    isCompassEnabled = false
                    isScaleBarEnabled = false
                    isZoomControlEnabled = false
                    isLocationButtonEnabled = false
                }
            }
            binding.btnLocation.map = naverMap
        }
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