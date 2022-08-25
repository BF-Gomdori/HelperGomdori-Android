package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.body.PostUser
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body

interface ApiService {

    @GET("/users")
    suspend fun getData(): Data

    @POST("/auth/barrierfree")
    suspend fun postMember(@Body postUser: PostUser)
}