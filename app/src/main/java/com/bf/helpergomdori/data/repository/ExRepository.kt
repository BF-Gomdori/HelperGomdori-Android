package com.bf.helpergomdori.data.repository

import com.bf.helpergomdori.data.remote.RemoteDataSource
import com.bf.helpergomdori.data.remote.RetrofitBuilder
import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.body.PostUser

object ExRepository : RemoteDataSource {
    private val apiService = RetrofitBuilder.apiService
    override suspend fun getData(): Data {
        return apiService.getData()
    }

    override suspend fun postMember(postUser: PostUser) {
        apiService.postMember(postUser)
    }
}