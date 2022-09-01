package com.bf.helpergomdori.data.repository

import com.bf.helpergomdori.data.remote.RemoteDataSourceImpl
import com.bf.helpergomdori.model.remote.body.NotificationBody
import javax.inject.Inject

class MainMapRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSourceImpl
) {

    suspend fun getHelpAccepted(authorization: String, token: String) =
        remoteDataSource.getHelpAccepted(authorization, token)

    suspend fun getBfCntAndGomdoriCnt() = remoteDataSource.getBfCntAndGomdoriCnt()

    suspend fun getHelpeePing(header: String) = remoteDataSource.getHelpeePing(header)

    suspend fun getHelperPing(header: String) = remoteDataSource.getHelperPing(header)

    suspend fun postPush(header: String, body: NotificationBody) = remoteDataSource.postPush(header, body)
}