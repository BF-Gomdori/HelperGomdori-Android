package com.bf.helpergomdori.data.dataSource

import com.bf.helpergomdori.data.ApiService
import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.remote.body.NotificationBody
import com.bf.helpergomdori.model.remote.body.PostUser
import com.bf.helpergomdori.model.remote.body.SigninBody
import com.bf.helpergomdori.model.remote.response.*
import com.bf.helpergomdori.model.websocket.Location
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher
) : RemoteDataSource {

    override suspend fun getData(): Flow<Data> = flow {
        emit(apiService.getData())
    }.flowOn(ioDispatcher)


    /**
     * 회원가입
     */
    override suspend fun postMember(postUser: PostUser): Token {
        return apiService.postMember(postUser)
    }


    override suspend fun postHelpee(header: String, signinBody: SigninBody): HelpeeResponse {
        return apiService.postHelpee(header, signinBody)
    }

    override suspend fun postHelper(header: String): HelperResponse {
        return apiService.postHelper(header)
    }


    /**
     * Main 지도
     */
    override suspend fun getHelpAccepted(headers: Map<String, String>) {
        return apiService.getHelpAccepted(headers)
    }

    override suspend fun getBfCntAndGomdoriCnt(): Flow<UserCnt> = flow {
        emit(apiService.getBfCntAndGomdoriCnt())
    }.flowOn(ioDispatcher)

    override suspend fun getHelpeePing(headers: String): Flow<HelpeeDetailPing> = flow {
        emit(apiService.getHelpeePing(headers))
    }.flowOn(ioDispatcher)

    override suspend fun getHelperPing(headers: String): Flow<HelperDetailPing> = flow {
        emit(apiService.getHelperPing(headers))
    }.flowOn(ioDispatcher)


    /**
     * WebSocket
     */
    override suspend fun getWebSocket(header: String) {
        apiService.getWebSocket(header)
    }

    /**
     * Request
     */
    override suspend fun postRequestInfo(
        header: String,
        location: Location
    ): Flow<RequestInfoResponse> = flow {
        emit(apiService.postRequestInfo(header, location))
    }.flowOn(ioDispatcher)

    /**
     * FCM
     */
    override suspend fun postPush(header: String, body: NotificationBody): NotificationResponse {
        return apiService.postPush(header, body)
    }


}