package com.bf.helpergomdori.ui.signIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bf.helpergomdori.data.repository.ExRepository
import com.bf.helpergomdori.model.response.PostUser
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel(){

    fun postUserInfo(){
        viewModelScope.launch {
            val accessToken = "AqetotdZ4XZiR9wOf8JubC8Bb5S4Et9lCbjWP0ZGCj102wAAAYLJu_P_"
            val phone = "01055924249"
            val name = "성공"
            val intro = "자기 소개"
            val age = 20
            ExRepository.postMember(PostUser(accessToken, phone, name, intro, age))
        }
    }
}