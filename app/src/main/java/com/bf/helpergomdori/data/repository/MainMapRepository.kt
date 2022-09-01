package com.bf.helpergomdori.data.repository

import com.bf.helpergomdori.data.remote.RemoteDataSourceImpl
import com.bf.helpergomdori.model.remote.body.NotificationBody
import com.bf.helpergomdori.model.remote.response.HelpeePing
import com.bf.helpergomdori.model.remote.response.HelperPing
import com.bf.helpergomdori.model.remote.response.NotificationResponse
import com.bf.helpergomdori.model.remote.response.UserCnt
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainMapRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSourceImpl
) {

    suspend fun getHelpAccepted(authorization: String, token: String) =
        remoteDataSource.getHelpAccepted(authorization, token)

    suspend fun getBfCntAndGomdoriCnt(): Flow<UserCnt> = remoteDataSource.getBfCntAndGomdoriCnt()

    suspend fun getHelpeePing(header: String): HelpeePing = remoteDataSource.getHelpeePing(header)

    suspend fun getHelperPing(header: String): HelperPing = remoteDataSource.getHelperPing(header)

    suspend fun postPush(header: String, body: NotificationBody): NotificationResponse = remoteDataSource.postPush(header, body)
}