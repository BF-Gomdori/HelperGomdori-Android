package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.remote.DefaultHeader
import com.bf.helpergomdori.model.remote.body.NotificationBody
import com.bf.helpergomdori.model.remote.body.PostUser
import com.bf.helpergomdori.model.remote.body.SigninBody
import com.bf.helpergomdori.model.remote.response.*
import com.bf.helpergomdori.utils.HEADER_KEY
import com.bf.helpergomdori.utils.HEADER_TOKEN_KEY
import kotlinx.coroutines.flow.Flow
import retrofit2.http.*

interface RemoteDataSource {

    suspend fun getData(): Flow<Data>

    /**
     * 회원가입
     */
    suspend fun postMember(@Body postUser: PostUser) : Token

    suspend fun postHelpee(@Header(HEADER_KEY) header: String, @Body signinBody: SigninBody): HelpeeResponse

    suspend fun postHelper(@Header(HEADER_KEY) header: String): HelperResponse

    /**
     * Main 지도
     */
    suspend fun getHelpAccepted(@HeaderMap headers: Map<String, String>) // 도움 요청이 수락되었을 때 웹소켓으로 수락되었음을 받았을 때

    suspend fun getBfCntAndGomdoriCnt(): Flow<UserCnt> // 베프 숫자 & 곰돌이 숫자

    suspend fun getHelpeePing(@Header(HEADER_KEY) header: String): Flow<HelpeeDetailPing>

    suspend fun getHelperPing(@Header(HEADER_KEY) header: String): Flow<HelperDetailPing>


    /**
     * WebSocket
     */
    suspend fun getWebSocket(@Header(HEADER_KEY) header: String)

    /**
     * FCM
     */
    suspend fun postPush(@Header(HEADER_KEY) header: String, @Body body: NotificationBody): NotificationResponse
}