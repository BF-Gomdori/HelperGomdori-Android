package com.bf.helpergomdori.ui.main


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Camera
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseActivity
import com.bf.helpergomdori.databinding.ActivityMainBinding
import com.bf.helpergomdori.utils.CAMERA_ZOOM_DENSITY
import com.bf.helpergomdori.utils.DensityUtil
import com.bf.helpergomdori.utils.LOCATION_PERMISSION_REQUEST_CODE
import com.bf.helpergomdori.utils.MAIN_TAG
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.openlocationcode.OpenLocationCode
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.overlay.OverlayImage
import com.naver.maps.map.util.FusedLocationSource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main), OnMapReadyCallback {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource
    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            viewModel.getLocationPermission(permissions) { isNotPermitted ->
                if (isNotPermitted) {
                    Toast.makeText(this, R.string.go_settings_and_allow_location, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun createActivity() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        if (!isLocationPermitted()) {
            requestLocationPermission()
        }
        initView()
        setListener()
    }


    private fun initView() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment?
            ?: MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.map_fragment, it).commit()
            }
        mapFragment.getMapAsync(this)

    }

    private fun setListener() {
        binding.apply {
            btnMyLocation.setOnClickListener {
                val latitude = viewModel.currentLocation["x"]
                val longitude = viewModel.currentLocation["y"]
                if (latitude != 0.0 && longitude != 0.0) {
                    val cameraPosition = CameraPosition(LatLng(latitude!!, longitude!!), CAMERA_ZOOM_DENSITY)
                    naverMap.moveCamera(CameraUpdate.toCameraPosition(cameraPosition))
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(nmap: NaverMap) {
        naverMap = nmap

        naverMap.locationSource = locationSource // 내장 위치 추적 기능
        fusedLocationClient.lastLocation.addOnSuccessListener { currentLocation ->
            Log.d(MAIN_TAG, "location : $currentLocation")
            viewModel.setCurrentLocation(currentLocation.latitude, currentLocation.longitude)

            naverMap.apply {
                mapType = NaverMap.MapType.Basic
                setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true)
                val cameraPosition = CameraPosition(
                    LatLng(currentLocation.latitude, currentLocation.longitude),
                    CAMERA_ZOOM_DENSITY
                )
                naverMap.moveCamera(CameraUpdate.toCameraPosition(cameraPosition))
                locationOverlay.run {
                    isVisible = true
                    position = LatLng(currentLocation.latitude, currentLocation.longitude)
                }
                locationTrackingMode = LocationTrackingMode.Follow
                isIndoorEnabled = true
            }
        }

        val gomdori = Marker().apply {
            val location = OpenLocationCode("8Q98FXV5+QX").decode() // 숭실대 정보과학관
            position = LatLng(location.centerLatitude, location.centerLongitude)
            icon = OverlayImage.fromResource(R.drawable.ic_marker_gomdori)
            DensityUtil.setResouces(resources)
            width = DensityUtil.dp2px(80f).toInt()
            height = DensityUtil.dp2px(80f).toInt()
            map = naverMap
        }

        gomdori.setOnClickListener {
            val profileDialog = ProfileDialog().apply {
                isCancelable = true
            }
            profileDialog.show(supportFragmentManager,"ProfileDialog")
            true
        }

        val bf = Marker().apply {
            val location = OpenLocationCode("8Q98FXW5+FC").decode() // 숭실대 도서관
            position = LatLng(location.centerLatitude, location.centerLongitude)
            icon = OverlayImage.fromResource(R.drawable.ic_marker_bf)
            DensityUtil.setResouces(resources)
            width = DensityUtil.dp2px(65f).toInt()
            height = DensityUtil.dp2px(65f).toInt()
            map = naverMap
        }

    }

    /**
     * Location Permission
     */

    private fun isLocationPermitted(): Boolean {
        locationSource = FusedLocationSource(this, LOCATION_PERMISSION_REQUEST_CODE)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(MAIN_TAG, "isLocationPermitted : NOT GRANTED")
            return false
        }
        Log.d(MAIN_TAG, "isLocationPermitted : GRANTED")
        return true
    }

    private fun requestLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }


}