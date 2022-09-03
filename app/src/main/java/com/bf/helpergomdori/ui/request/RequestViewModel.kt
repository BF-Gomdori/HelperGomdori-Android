package com.bf.helpergomdori.ui.request

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.data.repository.MainMapRepository
import com.bf.helpergomdori.model.remote.response.RequestInfoResponse
import com.bf.helpergomdori.model.websocket.Location
import com.bf.helpergomdori.utils.REQUEST_TAG
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import okhttp3.internal.notify

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val mainMapRepository: MainMapRepository
) : ViewModel() {

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
    }

    fun setDetailAddress(address: String) {
        _detailAddress = address
    }
}