package com.bf.helpergomdori.ui.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.data.repository.ExRepository
import com.bf.helpergomdori.model.response.PostUser
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel(){

    fun postUserInfo(accessToken: String){
        viewModelScope.launch {
            val phone = "01055924249"
            val name = "성공"
            val intro = "자기 소개"
            val age = 20
            val jwt  = ExRepository.postMember(PostUser(accessToken, phone, name, intro, age)) //todo 저장해서 api 보낼 때마다 헤더에 추가해서 보내줘야함
        }
    }
}