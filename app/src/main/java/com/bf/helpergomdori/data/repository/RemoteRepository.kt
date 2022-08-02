package com.bf.helpergomdori.data.repository


import com.bf.helpergomdori.data.remote.RemoteDataSourceImpl
import kotlinx.coroutines.CoroutineScope

class RemoteRepository(
    private val remoteDataSource: RemoteDataSourceImpl,
    private val externalScope: CoroutineScope
) {
}