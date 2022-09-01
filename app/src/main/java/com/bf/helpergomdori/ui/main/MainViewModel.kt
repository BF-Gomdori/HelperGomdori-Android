package com.bf.helpergomdori.ui.main

import android.Manifest
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.data.repository.LoginRepository
import com.bf.helpergomdori.data.repository.MainMapRepository
import com.bf.helpergomdori.model.local.Gender
import com.bf.helpergomdori.model.local.ProfileBf
import com.bf.helpergomdori.model.local.ProfileGomdori
import com.bf.helpergomdori.model.remote.body.MessageData
import com.bf.helpergomdori.model.remote.body.NotificationBody
import com.bf.helpergomdori.model.remote.body.NotificationData
import com.bf.helpergomdori.utils.MAIN_TAG
import com.bf.helpergomdori.utils.NotificationUtil.getFirebaseToken
import com.bf.helpergomdori.utils.PUSH_TAG
import com.bf.helpergomdori.utils.WebSocketUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainMapRepository: MainMapRepository
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
        WebSocketUtil.runStomp()
        postPush()

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
            longitude = 126.9585625
        )
        addBfList(bf)
    }

    fun getGomdoriProfile() {
        //todo api로 gomdori 정보 받아오기
        //_selectedGomdori.value =
    }

    fun addGomdoriList(gomdori: ProfileGomdori) {
        _gomdoriList.value.add(gomdori)
    }

    fun addBfList(bf: ProfileBf) {
        _bfList.value.add(bf)
    }

    fun setSelectedGomdori(gomdori: ProfileGomdori?) {
        _selectedGomdori.value = gomdori
    }

    fun setSelectedBf(bf: ProfileBf?) {
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

    fun postPush(){
        getFirebaseToken()
        val token = PrefsUtil.getFirebaseToken()
        val jwt = PrefsUtil.getJwt()
        Log.d(PUSH_TAG, "postPush: $token, $jwt")
        viewModelScope.launch {
            if (token != "" && jwt != "") {
                //mainMapRepository.postPush(jwt, NotificationBody(MessageData(NotificationData(), token)))
            }
        }
    }

}