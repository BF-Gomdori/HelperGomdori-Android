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

    fun postUser(accessToken: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val phone = "01055924249"
            val name = "밍도리"
            val intro = "자기 소개"
            val age = 20
            val jwt = remoteRepository.postUserInfo(
                PostUser(
                    accessToken,
                    phone,
                    name,
                    intro,
                    age
                )
            ) //todo 저장해서 api 보낼 때마다 헤더에 추가해서 보내줘야함
            //updateUserInfo(jwt, name, photoLink, phone, intro, gender, type)
        }
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