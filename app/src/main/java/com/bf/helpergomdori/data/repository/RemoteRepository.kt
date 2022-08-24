package com.bf.helpergomdori.data.repository


import com.bf.helpergomdori.data.remote.RemoteDataSource
import com.bf.helpergomdori.data.remote.RemoteDataSourceImpl
import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.response.PostUser
import kotlinx.coroutines.CoroutineScope

class RemoteRepository(
    private val remoteDataSource: RemoteDataSource
    //, private val externalScope: CoroutineScope
) {
    suspend fun getData(): Data = remoteDataSource.getData()

    suspend fun postUserInfo(postUser: PostUser) = remoteDataSource.postMember(postUser)
}

