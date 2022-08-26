package com.bf.helpergomdori.ui.main


import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseActivity
import com.bf.helpergomdori.databinding.ActivityMainBinding
import com.bf.helpergomdori.utils.LOCATION_PERMISSION_REQUEST_CODE
import com.bf.helpergomdori.utils.MAIN_TAG
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.LocationTrackingMode
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
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
    }


    private fun initView() {
        // 지도 객체 생성
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment
        mapFragment.getMapAsync(this)


    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(nmap: NaverMap) {
        naverMap = nmap

        naverMap.locationSource = locationSource // 내장 위치 추적 기능
        fusedLocationClient.lastLocation.addOnSuccessListener { currentLocation ->
            Log.d(MAIN_TAG, "location : $currentLocation")

            naverMap.locationOverlay.run {
                isVisible = true
                position = LatLng(currentLocation.latitude, currentLocation.longitude)
            }

            naverMap.locationTrackingMode = LocationTrackingMode.Follow
//                val marker = Marker().apply {
//                    icon = OverlayImage.fromResource(R.drawable.img_helper_gomdori)
//                    map = nmap
//                }
//
//                marker.position = LatLng(
//                    currentLocation.latitude, currentLocation.longitude
//                )
        }

    }


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