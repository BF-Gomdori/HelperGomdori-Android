package com.bf.helpergomdori.ui.main


import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bf.helpergomdori.R
import com.bf.helpergomdori.base.BaseActivity
import com.bf.helpergomdori.databinding.ActivityMainBinding
import com.bf.helpergomdori.utils.MAIN_TAG
import com.naver.maps.map.MapFragment


class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private lateinit var mapFragment: MapFragment

    val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Log.d(MAIN_TAG, "createActivity: ACCESS_FINE_LOCATION")
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.d(MAIN_TAG, "createActivity: ACCESS_COARSE_LOCATION")
                }
                else -> {
                    val should = shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)
                    Log.d(MAIN_TAG, "createActivity: NOT ACCEPTED LOCATION $should")
                    Toast.makeText(this, "설정 > 애플리케이션 > 도움곰돌이에서 위치 권한을 허용해주세요", Toast.LENGTH_LONG)
                }
            }
        }

    override fun createActivity() {
        binding.apply {}

        locationPermissionRequest.launch(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION))


        // 지도 객체 생성
        mapFragment = supportFragmentManager.findFragmentById(R.id.map_fragment) as MapFragment



    }

}