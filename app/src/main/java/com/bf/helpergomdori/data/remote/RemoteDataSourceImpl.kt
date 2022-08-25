package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.body.PostUser
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher? = null
) : RemoteDataSource {

    override suspend fun getData(): Data {
        return apiService.getData()
    }

    override suspend fun postMember(postUser: PostUser) {
        return apiService.postMember(postUser)
    }
}