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
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.naver.maps.geometry.LatLng
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
import com.teamfillin.fillin.data.service.StudioService
import com.teamfillin.fillin.databinding.ActivityStudioMapBinding
import com.teamfillin.fillin.presentation.dialog.PhotoDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject


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
        viewModel.location.observe(this) { location ->
            val markers = Marker().apply {
                position = location
                icon = OverlayImage.fromResource(R.drawable.ic_place_select)
                this.map = naverMap
            }
            markers.setOnClickListener {
                viewModel.studioIdHash[location]?.let {
                    viewModel.studioDetail(it)
                    viewModel.studioPhoto(it)
                }
                true
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

            if (it.site.isNullOrEmpty()) binding.tvLink.visibility = View.GONE
            else {
                binding.tvLink.visibility = View.VISIBLE
                linkText(it.site)
            }
            linkText(it.site)
        }

        viewModel.photos.observe(this) {
            photoReviewAdapter.submitList(it)
        }

        viewModel.cameraZoom.observe(this, EventObserver {
            val cameraUpdate = CameraUpdate.scrollTo(viewModel.locationHash[it]!!)
            naverMap?.moveCamera(cameraUpdate)
        })
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