package com.bf.helpergomdori.data.repository

import androidx.datastore.core.DataStore
import com.bf.helpergomdori.UserInfo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInfoRepository @Inject constructor(
    private val dataStore: DataStore<UserInfo>
) {
    fun getUserInfo(): Flow<UserInfo> {
        return dataStore.data
    }


    suspend fun updateUserInfo(jwt: String, name: String, phone: String){
        dataStore.updateData {
            it.toBuilder().apply {
                setJwt(jwt)
                setName(name)
                setPhone(phone)
            }.build()
        }
    }

    suspend fun updateUserInfo(jwt: String, name: String, photoLink: String, phone: String, intro: String, gender: UserInfo.Gender, type: UserInfo.Type) {
        dataStore.updateData {
            it.toBuilder().apply{
                setJwt(jwt)
                setName(name)
                setPhotoLink(photoLink)
                setPhone(phone)
                setIntro(intro)
                setGender(gender)
                setType(type)
            }.build()
        }
    }
}
