package com.bf.helpergomdori.data

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.remote.body.NotificationBody
import com.bf.helpergomdori.model.remote.body.PostUser
import com.bf.helpergomdori.model.remote.body.SigninBody
import com.bf.helpergomdori.model.remote.response.*
import com.bf.helpergomdori.model.websocket.Location
import com.bf.helpergomdori.utils.HEADER_KEY
import retrofit2.http.*

interface ApiService {

    @GET("/users")
    suspend fun getData(): Data

    /**
     * 회원가입
     */
    @POST("/auth/barrierfree")
    suspend fun postMember(@Body postUser: PostUser) : Token

    @POST("/auth/signup/helpee")
    suspend fun postHelpee(@Header(HEADER_KEY) header: String, @Body signinBody: SigninBody): HelpeeResponse

    @POST("/auth/signup/helper")
    suspend fun postHelper(@Header(HEADER_KEY) header: String): HelperResponse


    /**
     * Main 지도
     */
    @GET("/api/accept")
    suspend fun getHelpAccepted(@HeaderMap headers: Map<String, String>) // 도움 요청이 수락되었을 때 웹소켓으로 수락되었음을 받았을 때

    @GET("/api/connect/users")
    suspend fun getBfCntAndGomdoriCnt(): UserCnt // 베프 숫자 & 곰돌이 숫자

    @GET("/api/helpee/ping")
    suspend fun getHelpeePing(@Header(HEADER_KEY) header: String): HelpeeDetailPing

    @GET("/api/helper/ping")
    suspend fun getHelperPing(@Header(HEADER_KEY) header: String): HelperDetailPing

    /**
     * WebSocket
     */
    @GET("/send")
    suspend fun getWebSocket(@Header(HEADER_KEY) header: String)

    /**
     * Request
     */
    @POST("/api/user/semi/info")
    suspend fun postRequestInfo(@Header(HEADER_KEY) header: String, @Body location: Location): RequestInfoResponse

    /**
     * FCM
     */
    @POST("/fcm/push")
    suspend fun postPush(@Header(HEADER_KEY) header: String, @Body body: NotificationBody): NotificationResponse
}