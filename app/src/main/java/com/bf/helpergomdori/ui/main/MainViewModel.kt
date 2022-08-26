package com.bf.helpergomdori.ui.main

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.ViewModel
import com.bf.helpergomdori.data.repository.RemoteRepository
import com.bf.helpergomdori.utils.MAIN_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.system.exitProcess

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    fun getLocationPermission(permissions: Map<String, Boolean>, isNotPermitted: (Boolean) -> Unit) {
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                Log.d(MAIN_TAG, "createActivity: ACCESS_FINE_LOCATION")
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                Log.d(MAIN_TAG, "createActivity: ACCESS_COARSE_LOCATION")
            }
            else -> {
                Log.d(MAIN_TAG, "createActivity: NOT ACCEPTED LOCATION")
                isNotPermitted(true)
            }
        }
    }

}