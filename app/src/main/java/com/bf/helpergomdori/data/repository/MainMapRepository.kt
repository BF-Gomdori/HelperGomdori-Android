package com.bf.helpergomdori.data.repository

import com.bf.helpergomdori.data.dataSource.RemoteDataSourceImpl
import com.bf.helpergomdori.model.remote.body.NotificationBody
import com.bf.helpergomdori.model.remote.response.*
import com.bf.helpergomdori.model.websocket.Location
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MainMapRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSourceImpl
) {

    suspend fun getHelpAccepted(headers: Map<String, String>) =
        remoteDataSource.getHelpAccepted(headers)

    suspend fun getBfCntAndGomdoriCnt(): Flow<UserCnt> = remoteDataSource.getBfCntAndGomdoriCnt()

    suspend fun getHelpeePing(header: String): Flow<HelpeeDetailPing> =
        remoteDataSource.getHelpeePing(header)

    suspend fun getHelperPing(header: String): Flow<HelperDetailPing> =
        remoteDataSource.getHelperPing(header)

    suspend fun getSendWebSocket(header: String) = remoteDataSource.getWebSocket(header)

    suspend fun postRequestInfo(header: String, location: Location): Flow<RequestInfoResponse> =
        remoteDataSource.postRequestInfo(header, location)

    suspend fun postPush(header: String, body: NotificationBody): NotificationResponse =
        remoteDataSource.postPush(header, body)
}