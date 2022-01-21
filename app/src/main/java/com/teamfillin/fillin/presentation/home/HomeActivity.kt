package com.teamfillin.fillin.presentation.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import com.teamfillin.fillin.R
import com.teamfillin.fillin.core.base.BindingActivity
import com.teamfillin.fillin.core.content.receive
import com.teamfillin.fillin.core.context.toast
import com.teamfillin.fillin.core.view.setOnSingleClickListener
import com.teamfillin.fillin.data.response.BaseResponse
import com.teamfillin.fillin.data.response.ResponseNewPhotoInfo
import com.teamfillin.fillin.data.response.ResponseUserInfo
import com.teamfillin.fillin.data.service.HomeService
import com.teamfillin.fillin.data.service.StudioService
import com.teamfillin.fillin.databinding.ActivityHomeBinding
import com.teamfillin.fillin.presentation.add.AddPhotoActivity
import com.teamfillin.fillin.presentation.filmroll.FilmRollActivity
import com.teamfillin.fillin.presentation.map.StudioMapActivity
import com.teamfillin.fillin.presentation.my.MyPageActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home),
    OnMapReadyCallback {
    @Inject
    lateinit var service: HomeService
    @Inject
    lateinit var studioService: StudioService
    private lateinit var newPhotosAdapter: NewPhotosAdapter
    private lateinit var fusedLocationSource: FusedLocationSource
    private var activityNaverMap: NaverMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mapMain.onCreate(savedInstanceState)
        fusedLocationSource =
            FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        binding.mapMain.getMapAsync(this)
        initNewPhotoRecyclerView()
        initDatas()
        clickListener()
        popup()
    }

    private fun initDatas() {
//        service.getNewPhoto().receive({
//            newPhotosAdapter.replaceList(it.data.photos)
//        }, {
//            Timber.d("Error $it")
//        })

        lifecycleScope.launch {
            runCatching {
                service.getNewPhoto().await()
            }.onSuccess {
                newPhotosAdapter.replaceList(it.data.photos)
            }.onFailure(Timber::e)
        }

        service.getUser().enqueue(object : Callback<BaseResponse<ResponseUserInfo>> {
            override fun onResponse(
                call: Call<BaseResponse<ResponseUserInfo>>,
                response: Response<BaseResponse<ResponseUserInfo>>
            ) {
                if (response.isSuccessful) {
                    val userData = response.body()?.data
                    binding.tvIntro.text = "${userData?.user?.nickname}"
                    Timber.d("데이터 넘어오나?", "${userData?.user?.nickname}")
                } else {
                    Timber.d("Error")
                }
            }

            override fun onFailure(call: Call<BaseResponse<ResponseUserInfo>>, t: Throwable) {
                Log.d("NetworkTest", "error: $t")
            }
        })
    }

    private fun clickListener() {
        binding.btnAddphoto.setOnSingleClickListener {
            val intent = Intent(this, AddPhotoActivity::class.java)
            startActivity(intent)
        }

        binding.btnFilmroll.setOnSingleClickListener {
            val intent = Intent(this, FilmRollActivity::class.java)
            startActivity(intent)
        }
        binding.btnStudiomap.setOnSingleClickListener {
            val intent = Intent(this, StudioMapActivity::class.java)
            startActivity(intent)
        }
        binding.btnMypage.setOnSingleClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initNewPhotoRecyclerView() {
        newPhotosAdapter = NewPhotosAdapter()
        binding.rvNewPhotos.adapter = newPhotosAdapter
    }

    private fun popup() {
        binding.apply {
            btnClose.setOnClickListener {
                clPopup.isVisible = !clPopup.isVisible
            }
            tvNotice.setOnClickListener {
                toast("현상소 제보 Page이동")
            }
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
            minZoom = 6.0
            maxZoom = 18.0
            uiSettings.run {
                isCompassEnabled = false
                isScaleBarEnabled = false
                isZoomControlEnabled = false
                isLocationButtonEnabled = false
            }
        }
        markerLocationEvent()
        activityNaverMap?.setOnMapClickListener { pointF, latLng ->
            val intent = Intent(this, StudioMapActivity::class.java)
            startActivity(intent)
        }
    }

    private fun markerLocationEvent() {
        lifecycleScope.launch {
            runCatching {
                studioService.getWholeStudio().await()
            }.onSuccess {
                it.data.studios.forEach {
                    Marker().apply {
                        position = LatLng(it.lati, it.long)
                        icon = OverlayImage.fromResource(R.drawable.ic_place_select)
                        this.map = activityNaverMap
                    }
                }
            }.onFailure(Timber::e)
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
        @JvmStatic
        fun getIntent(context: Context) = Intent(context, HomeActivity::class.java).apply {
            flags =
                Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        }

        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }

}


