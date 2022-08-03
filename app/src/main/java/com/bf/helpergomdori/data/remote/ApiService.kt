package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getData(): Data
}