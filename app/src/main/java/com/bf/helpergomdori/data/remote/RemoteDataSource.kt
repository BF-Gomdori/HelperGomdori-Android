package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.body.PostUser
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body

interface RemoteDataSource {

    suspend fun getData(): Flow<Data>

    suspend fun postMember(@Body postUser: PostUser)
}