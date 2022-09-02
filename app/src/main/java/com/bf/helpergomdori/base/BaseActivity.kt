package com.bf.helpergomdori.base

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.bf.helpergomdori.R
import com.bf.helpergomdori.utils.LOCATION_PERMISSION_REQUEST_CODE
import com.bf.helpergomdori.utils.MAIN_TAG
import com.google.android.gms.location.FusedLocationProviderClient
import com.naver.maps.map.util.FusedLocationSource

abstract class BaseActivity<B: ViewDataBinding>(private val resId: Int) : AppCompatActivity() {
    private var _binding: B? = null
    val binding get() = _binding!!

    private lateinit var locationSource: FusedLocationSource


    abstract fun createActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView<B>(this, resId)
        setContentView(binding.root)
        createActivity()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    /**
     * Location Permission
     */
    fun isLocationPermitted(): Boolean {
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

    private val locationPermissionRequest =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    Log.d(MAIN_TAG, "createActivity: ACCESS_FINE_LOCATION")
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    Log.d(MAIN_TAG, "createActivity: ACCESS_COARSE_LOCATION")
                }
                else -> {
                    Log.d(MAIN_TAG, "createActivity: NOT ACCEPTED LOCATION")
                    Toast.makeText(this, R.string.go_settings_and_allow_location, Toast.LENGTH_LONG)
                        .show()
                }
            }
        }

    fun requestLocationPermission() {
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    fun getFusedLocationSource(): FusedLocationSource? {
        if (this::locationSource.isInitialized) {
            return locationSource
        }
        return null
    }

}