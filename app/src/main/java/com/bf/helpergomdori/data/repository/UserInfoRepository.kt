package com.bf.helpergomdori.data.repository

import android.content.Context
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

    suspend fun updateUserInfo(name: String, ) {
        dataStore.updateData {
            it.toBuilder().setName(name).build()
        }
    }
}
