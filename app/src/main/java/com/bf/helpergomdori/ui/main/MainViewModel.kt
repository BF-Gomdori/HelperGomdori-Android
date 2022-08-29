package com.bf.helpergomdori.ui.main

import android.Manifest
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.lifecycle.ViewModel
import com.bf.helpergomdori.data.repository.RemoteRepository
import com.bf.helpergomdori.model.Gender
import com.bf.helpergomdori.model.ProfileBf
import com.bf.helpergomdori.model.ProfileGomdori
import com.bf.helpergomdori.utils.MAIN_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlin.system.exitProcess

@HiltViewModel
class MainViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository
) : ViewModel() {

    private var _currentLocation = mutableMapOf("x" to 0.0, "y" to 0.0)
    val currentLocation get() = _currentLocation

    private var _selectedGomdori: MutableStateFlow<ProfileGomdori?> = MutableStateFlow(null)
    val selectedGomdori get() = _selectedGomdori

    private var _selectedBf: MutableStateFlow<ProfileBf?> = MutableStateFlow(null)
    val selectedBf get() = _selectedBf

    private var _gomdoriList: StateFlow<MutableList<ProfileGomdori>> =
        MutableStateFlow(mutableListOf())
    val gomdoriList get() = _gomdoriList

    private var _bfList: StateFlow<MutableList<ProfileBf>> = MutableStateFlow(mutableListOf())
    val bfList get() = _bfList

    init {
        val gomdori = ProfileGomdori(
            jwt = "",
            type = "지체장애",
            name = "김철수",
            age = 27,
            gender = Gender.MALE,
            requested_term = "택시 타는 것을 도와주세요",
            location = "서울특별시 동작구 사당로 50",
            latitude = 37.494437500000004,
            longitude = 126.9599375
        )
        addGomdoriList(gomdori)

        val bf = ProfileBf(
            jwt = "",
            name = "김민주",
            age = 24,
            gender = Gender.FEMALE,
            location = "서울특별시 동작구 상도로 369",
            latitude = 37.496187500000005,
            longitude = 126.9585625)
        addBfList(bf)
    }

    fun getGomdoriProfile() {
        //todo api로 gomdori 정보 받아오기
        //_selectedGomdori.value =
    }
    
    fun addGomdoriList(gomdori: ProfileGomdori){
        _gomdoriList.value.add(gomdori)
    }

    fun addBfList(bf: ProfileBf) {
        _bfList.value.add(bf)
    }

    fun setSelectedGomdori(gomdori: ProfileGomdori?) {
        _selectedGomdori.value = gomdori
    }

    fun setSelectedBf(bf: ProfileBf?){
        _selectedBf.value = bf
    }

    fun setCurrentLocation(x: Double, y: Double) {
        _currentLocation["x"] = x
        _currentLocation["y"] = y
    }

    fun getLocationPermission(
        permissions: Map<String, Boolean>,
        isNotPermitted: (Boolean) -> Unit
    ) {
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