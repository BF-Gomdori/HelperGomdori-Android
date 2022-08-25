package com.bf.helpergomdori.data.repository


import com.bf.helpergomdori.data.remote.RemoteDataSourceImpl
import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.body.PostUser
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSourceImpl
    //, private val externalScope: CoroutineScope
) {
    suspend fun getData(): Data = remoteDataSource.getData()

    suspend fun postUserInfo(postUser: PostUser) = remoteDataSource.postMember(postUser)
}

