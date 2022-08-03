package com.bf.helpergomdori.data.repository


import com.bf.helpergomdori.data.remote.RemoteDataSource
import com.bf.helpergomdori.data.remote.RemoteDataSourceImpl
import com.bf.helpergomdori.model.Data
import kotlinx.coroutines.CoroutineScope

class RemoteRepository(
    private val remoteDataSource: RemoteDataSource,
    private val externalScope: CoroutineScope
) {
    suspend fun getData() = remoteDataSource.getData()
}