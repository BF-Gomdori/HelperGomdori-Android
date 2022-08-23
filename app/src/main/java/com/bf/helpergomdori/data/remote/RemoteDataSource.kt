package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.response.PostUser
import retrofit2.http.Body

interface RemoteDataSource {

    suspend fun getData(): Data

    suspend fun postMember(@Body postUser: PostUser)
}