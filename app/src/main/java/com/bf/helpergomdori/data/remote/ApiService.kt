package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.remote.body.PostUser
import com.bf.helpergomdori.model.remote.body.SigninBody
import com.bf.helpergomdori.model.remote.response.*
import com.bf.helpergomdori.utils.HEADER_KEY
import com.bf.helpergomdori.utils.HEADER_TOKEN_KEY
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Header

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
    @POST("/api/accept")
    suspend fun getHelpAccepted(@Header(HEADER_KEY) authorization: String, @Header(HEADER_TOKEN_KEY) token: String) // 도움 요청이 수락되었을 때 웹소켓으로 수락되었음을 받았을 때

    @POST("/api/connect/users")
    suspend fun getBfCntAndGomdoriCnt(): UserCnt // 베프 숫자 & 곰돌이 숫자

    @POST("/api/helpee/ping")
    suspend fun getHelpeePing(@Header(HEADER_KEY) header: String): HelpeePing

    @POST("/api/helper/ping")
    suspend fun getHelperPing(@Header(HEADER_KEY) header: String): HelperPing
}