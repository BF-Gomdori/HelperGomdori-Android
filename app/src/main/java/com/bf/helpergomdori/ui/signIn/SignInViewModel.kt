package com.bf.helpergomdori.ui.signIn

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.HelperGomdoriApplication.Companion.PrefsUtil
import com.bf.helpergomdori.UserInfo
import com.bf.helpergomdori.data.repository.RemoteRepository
import com.bf.helpergomdori.data.repository.UserInfoRepository
import com.bf.helpergomdori.model.remote.DefaultHeader
import com.bf.helpergomdori.model.remote.body.PostUser
import com.bf.helpergomdori.model.remote.body.SigninBody
import com.bf.helpergomdori.utils.SIGNIN_TAG
import com.bf.helpergomdori.utils.SharedPreferencesUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
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
            val jwt = remoteRepository.postUserInfo(newUser).token
            PrefsUtil.setJwt(jwt)
            Log.d(SIGNIN_TAG, "postUser: ${jwt}")
            updateUserInfo(jwt, newUser.name!!, newUser.phone!!)
        }
    }

    fun postHelpee(disableTypeList: MutableList<String>) {
        getUserInfo()
        viewModelScope.launch {
            Log.d(SIGNIN_TAG, "postHelpee: ${currentUserInfo.value?.jwt}")
            if (currentUserInfo.value != null) {
                val header = DefaultHeader("Bearer "+ currentUserInfo.value!!.jwt)
                val body = SigninBody(disableTypeList[0], null) //todo api에서 장애유형 여러 개 받을 수 있게되면 list로 보내 주기
                remoteRepository.runCatching {
                    postHelpee(header, body)
                }.onFailure {
                    when(it) {
                        is HttpException -> {
                            Log.e(SIGNIN_TAG, "postHelper: httpException $it" )
                        }
                        else -> {
                            Log.e(SIGNIN_TAG, "postHelper: $it")
                        }
                    }
                }
            }
        }
    }

    fun postHelper() {
        getUserInfo()
        viewModelScope.launch {
            Log.d(SIGNIN_TAG, "postHelper: ${currentUserInfo.value?.jwt}")
            if (currentUserInfo.value != null) {
                val header = DefaultHeader("Bearer "+ currentUserInfo.value!!.jwt)
                remoteRepository.runCatching {
                    postHelper(header)
                }.onFailure {
                    when(it) {
                        is HttpException -> {
                            Log.e(SIGNIN_TAG, "postHelper: httpException ${it.message()}" )
                        }
                        else -> {
                            Log.e(SIGNIN_TAG, "postHelper: $it")
                        }
                    }
                }
            }
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


    fun updateUserInfo(
        jwt: String,
        name: String,
        phone: String,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.updateUserInfo(jwt = jwt, name = name, phone = phone)
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            userRepository.getUserInfo().collect {
                _currentUserInfo.value = it
            }
        }
    }
}