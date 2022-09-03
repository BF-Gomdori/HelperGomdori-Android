package com.bf.helpergomdori.ui.request

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.base.BaseViewModel
import com.bf.helpergomdori.data.repository.MainMapRepository
import com.bf.helpergomdori.model.remote.response.RequestInfoResponse
import com.bf.helpergomdori.model.websocket.HelpRequest
import com.bf.helpergomdori.model.websocket.Location
import com.bf.helpergomdori.utils.REQUEST_TAG
import com.bf.helpergomdori.utils.WebSocketUtil
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.internal.notify

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val mainMapRepository: MainMapRepository
) : BaseViewModel() {

    private var _currentLocation: Location? = null
    val currentLocation get() = _currentLocation

    private var _requestInfo: MutableStateFlow<RequestInfoResponse?> = MutableStateFlow(null)
    val requestInfo get() = _requestInfo

    private var _specificRequest: String = ""
    val specificRequest get() = _specificRequest

    private var _needSituations: List<String> = mutableListOf()
    val needSituations get() = _needSituations

    private var _detailAddress: String = ""
    val detailAddress get() = _detailAddress

    private val websocket: WebSocketUtil = WebSocketUtil(this)

    /**
     * Api Process
     */
    fun postRequestInfo(location: Location) {
        val jwt = PrefsUtil.getJwt()
        viewModelScope.launch {
            mainMapRepository.postRequestInfo(jwt, location).runCatching {
                collect {
                    Log.d(REQUEST_TAG, "postRequestInfo: ${it}")
                    _requestInfo.value = it
                }
            }
        }
    }

    /**
     * WebSocket
     */
    fun sendHelpMessage(){
        websocket.setCurrentLocation(currentLocation!!)
        val helpRequest = HelpRequest(
            helpeeJwt = PrefsUtil.getJwt(),
            requestType = needSituations[0],
            requestDetail = specificRequest,
            detailLocation = detailAddress
        )
        websocket.sendHelpMessage(helpRequest)
    }

    /**
     * Get & Set
     */
    fun setCurrentLocation(location: Location) {
        _currentLocation = location
    }

    fun setSpecificRequest(text: String) {
        _specificRequest = text
    }

    fun setNeedSituations(list: List<String>) {
        _needSituations = list
        Log.d(REQUEST_TAG, "setNeedSituations: ${needSituations}")
    }

    fun setDetailAddress(address: String) {
        _detailAddress = address
    }
}