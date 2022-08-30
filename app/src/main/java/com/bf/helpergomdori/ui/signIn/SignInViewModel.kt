package com.bf.helpergomdori.ui.signIn

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.UserInfo
import com.bf.helpergomdori.data.repository.RemoteRepository
import com.bf.helpergomdori.data.repository.UserInfoRepository
import com.bf.helpergomdori.model.body.PostUser
import com.bf.helpergomdori.utils.SIGNIN_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val userRepository: UserInfoRepository
) : ViewModel() {

    private var _currentUserInfo: MutableStateFlow<UserInfo?> = MutableStateFlow(null)
    val currentUserInfo get() = _currentUserInfo

    private var _newUser = PostUser()
    val newUser get() = _newUser

    fun postUser() {
        CoroutineScope(Dispatchers.IO).launch {
            val jwt = remoteRepository.postUserInfo(newUser)
            Log.d(SIGNIN_TAG, "postUser: ${jwt}")
            //updateUserInfo(jwt, name, photoLink, phone, intro, gender, type)
        }
    }

    fun setUserAccessToken(token: String) {
        _newUser.access_token = token
    }

    fun setUserPhone(phone: String) {
        _newUser.phone = phone
    }

    fun setUserName(name: String) {
        _newUser.name = name
    }



    fun updateUserInfo(jwt: String, name: String, photoLink: String, phone: String, intro: String, gender: UserInfo.Gender, type: UserInfo.Type) {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.updateUserInfo(jwt, name, photoLink, phone, intro, gender, type)
        }
    }

    fun getUserInfo(){
        viewModelScope.launch {
            userRepository.getUserInfo().collect {
                _currentUserInfo.value = it
            }
        }
    }
}