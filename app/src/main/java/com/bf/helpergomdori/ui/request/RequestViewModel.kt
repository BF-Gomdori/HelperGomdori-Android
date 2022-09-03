package com.bf.helpergomdori.ui.request

import android.util.Log
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.data.repository.MainMapRepository
import com.bf.helpergomdori.model.websocket.Location
import com.bf.helpergomdori.utils.REQUEST_TAG
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class RequestViewModel @Inject constructor(
    private val mainMapRepository: MainMapRepository
): ViewModel() {
    private var _name: MutableStateFlow<String> = MutableStateFlow("")
    val name = _name.asStateFlow()

    private var _image: MutableStateFlow<String> = MutableStateFlow("")
    val image get() = _image

    private var _locationAddress: MutableStateFlow<String> = MutableStateFlow("")
    val locationAddress get() = _locationAddress

    init {
    }

    fun postRequestInfo(location: Location){
        val jwt = PrefsUtil.getJwt()
        viewModelScope.launch {
            mainMapRepository.postRequestInfo(jwt, location).runCatching {
                collect{
                    Log.d(REQUEST_TAG, "postRequestInfo: ${it}")
                }

            }
        }
    }
}