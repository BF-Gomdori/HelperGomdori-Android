package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data

interface RemoteDataSource {

    suspend fun getData(): Data

}