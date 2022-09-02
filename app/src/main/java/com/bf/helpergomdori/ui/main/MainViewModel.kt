package com.bf.helpergomdori.ui.main

import android.Manifest
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.data.repository.MainMapRepository
import com.bf.helpergomdori.model.local.Gender
import com.bf.helpergomdori.model.local.HelpType
import com.bf.helpergomdori.model.local.ProfileBf
import com.bf.helpergomdori.model.local.ProfileGomdori
import com.bf.helpergomdori.model.remote.response.Ping
import com.bf.helpergomdori.model.websocket.Location
import com.bf.helpergomdori.utils.MAIN_TAG
import com.bf.helpergomdori.utils.NotificationUtil.getFirebaseToken
import com.bf.helpergomdori.utils.PUSH_TAG
import com.bf.helpergomdori.utils.WEBSOCKET_TAG
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

    private var _currentLocation = Location( 0.0,  0.0)
    val currentLocation get() = _currentLocation

    private var _selectedGomdori: MutableStateFlow<ProfileGomdori?> = MutableStateFlow(null)
    val selectedGomdori get() = _selectedGomdori

    private var _selectedBf: MutableStateFlow<ProfileBf?> = MutableStateFlow(null)
    val selectedBf get() = _selectedBf

    private var _gomdoriList: StateFlow<MutableList<Ping>> =
        MutableStateFlow(mutableListOf())
    val gomdoriList get() = _gomdoriList

    private var _bfList: StateFlow<MutableList<Ping>> = MutableStateFlow(mutableListOf())
    val bfList get() = _bfList

    private val webSocket: WebSocketUtil = WebSocketUtil(this)

    init {
        //postPush()
    }

    fun getGomdoriProfile() {
        //todo api로 gomdori 정보 받아오기
        //_selectedGomdori.value =
    }

    fun addGomdoriList(gomdori: Ping) {
        _gomdoriList.value.add(gomdori)
    }

    fun addBfList(bf: Ping) {
        _bfList.value.add(bf)
    }

    fun setSelectedGomdori(gomdori: ProfileGomdori?) {
        _selectedGomdori.value = gomdori
    }

    fun setSelectedBf(bf: ProfileBf?) {
        _selectedBf.value = bf
    }

    fun setCurrentLocation(x: Double, y: Double) {
        _currentLocation.x= x
        _currentLocation.y = y
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

    fun startWebSocket(){
        webSocket.runStomp()
        webSocket.setCurrentLocation(currentLocation)
    }

    fun receivePing(ping: Ping){
        Log.d(MAIN_TAG, "receivePing: $ping")
        if (ping.type == HelpType.GOMDORI) {
            addGomdoriList(ping)
        }else {
            addBfList(ping)
        }
    }

}