package com.bf.helpergomdori.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.base.BaseViewModel
import com.bf.helpergomdori.data.repository.MainMapRepository
import com.bf.helpergomdori.model.local.*
import com.bf.helpergomdori.model.remote.response.HelpeeDetailPing
import com.bf.helpergomdori.model.websocket.HelpRequest
import com.bf.helpergomdori.model.websocket.Location
import com.bf.helpergomdori.utils.*
import com.bf.helpergomdori.utils.NotificationUtil.getFirebaseToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.http.HEAD
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainMapRepository: MainMapRepository
) : BaseViewModel() {

    private var _currentLocation = Location(0.0, 0.0)
    val currentLocation get() = _currentLocation

    private var _selectedGomdori: MutableStateFlow<GomdoriDetailPing?> = MutableStateFlow(null)
    val selectedGomdori get() = _selectedGomdori

    private var _selectedBf: MutableStateFlow<BfDetailPing?> = MutableStateFlow(null)
    val selectedBf get() = _selectedBf

    private var _gomdoriList: MutableStateFlow<List<Ping>> =
        MutableStateFlow(listOf())
    val gomdoriList get() = _gomdoriList

    private var _disconnetedGomdoriList: MutableStateFlow<List<Ping>> = MutableStateFlow(listOf())
    val disconnectedGomdoriList get() = _disconnetedGomdoriList

    private var _bfList: MutableStateFlow<List<Ping>> = MutableStateFlow(listOf())
    val bfList get() = _bfList

    private var _disconnectedBfList: MutableStateFlow<List<Ping>> = MutableStateFlow(listOf())
    val disconnectedBfList get() = _disconnectedBfList

    private var _bfCnt: MutableStateFlow<Int> = MutableStateFlow(0)
    val bfCnt = _bfCnt.asStateFlow()

    private var _gomdoriCnt: MutableStateFlow<Int> = MutableStateFlow(0)
    val gomdoriCnt = _gomdoriCnt.asStateFlow()

    private val webSocket: WebSocketUtil = WebSocketUtil(this)


    /**
     * Api Process
     */
    fun sendWebSocketStarted(){
        val jwt = PrefsUtil.getJwt()
        viewModelScope.launch {
            mainMapRepository.getSendWebSocket(jwt)
        }
    }

    fun getUserCnt(){
        viewModelScope.launch {
            mainMapRepository.getBfCntAndGomdoriCnt().runCatching {
                collect{
                    Log.d(MAIN_TAG, "getUserCnt: ${it}")
                    _bfCnt.value = it.bf
                    _gomdoriCnt.value = it.gomdori
                }
            }
        }
    }

    fun getGomdoriPing(selectedJwt: String, latitude: Double, longitude: Double) {
        val otherJwt = "Bearer $selectedJwt"
        viewModelScope.launch {
            mainMapRepository.getHelpeePing(otherJwt).runCatching {
                collect {
                    Log.d(MAIN_TAG, "getGomdoriPing: $it")
                    _selectedGomdori.value = GomdoriDetailPing(it, latitude, longitude)
                }
            }
        }
    }

    fun getBfPing(selectedJwt: String, latitude: Double, longitude: Double) {
        val otherJwt = "Bearer $selectedJwt"
        viewModelScope.launch {
            mainMapRepository.getHelperPing(otherJwt).runCatching {
                collect {
                    Log.d(MAIN_TAG, "getBfPing: $it")
                    _selectedBf.value = BfDetailPing(it, latitude, longitude)
                }
            }.onFailure {
                it.printStackTrace()
                Log.e(MAIN_TAG, "getBfPing: ${it.message}")
            }
        }
    }


    /**
     * WebSocket Process
     */

    fun startWebSocket() {
        webSocket.runStomp()
        webSocket.setCurrentLocation(currentLocation)
    }

    fun receivePing(ping: Ping) {
        getUserCnt()
        Log.d(MAIN_TAG, "receivePing: $ping")
        if (ping.type == HelpType.GOMDORI) {
            addGomdoriList(ping)
        } else {
            addBfList(ping)
        }
    }

    fun deletePing(ping: Ping) {
        getUserCnt()
        removeGomdoriList(ping)
        removeBfList(ping)
    }

    fun sendAcceptedMessage(helpRequest: HelpRequest) {
        webSocket.sendAcceptMessage(helpRequest)
    }

    /**
     * Get & Set Process
     */

    fun addGomdoriList(gomdori: Ping) {
        val list = gomdoriList.value.toMutableList()
        list.add(gomdori)
        _gomdoriList.value = list
    }

    fun removeGomdoriList(gomdori: Ping) {
        val list = gomdoriList.value.toMutableList()
        val targetList = list.filter {
            it.location == gomdori.location
        }
        targetList.forEach {
            list.remove(it)
        }
        _disconnetedGomdoriList.value = targetList
        _gomdoriList.value = list
    }

    fun addBfList(bf: Ping) {
        val list = bfList.value.toMutableList()
        list.add(bf)
        _disconnectedBfList.value = list
        _bfList.value = list
    }

    fun removeBfList(bf: Ping) {
        val list = bfList.value.toMutableList()
        val targetList = list.filter {
            it.location == bf.location
        }
        targetList.forEach {
            list.remove(it)
        }
        _disconnectedBfList.value = targetList
        _bfList.value = list
    }

    fun setCurrentLocation(x: Double, y: Double) {
        _currentLocation.x = x
        _currentLocation.y = y
    }

    fun setSelectedGomdori(gomdori: GomdoriDetailPing?) {
        _selectedGomdori.value = gomdori
    }

    fun setSelectedBf(bf: BfDetailPing?) {
        _selectedBf.value = bf
    }
}