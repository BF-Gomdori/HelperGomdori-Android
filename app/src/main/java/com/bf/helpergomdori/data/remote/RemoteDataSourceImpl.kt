package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import kotlinx.coroutines.CoroutineDispatcher

class RemoteDataSourceImpl(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) : RemoteDataSource {

    override suspend fun getData(): Data {
        return apiService.getData()
    }
}