package com.kimchuu.helpergomdori.data.repository


import com.kimchuu.helpergomdori.data.remote.RemoteDataSourceImpl
import kotlinx.coroutines.CoroutineScope

class RemoteRepository(
    private val remoteDataSource: RemoteDataSourceImpl,
    private val externalScope: CoroutineScope
) {
}