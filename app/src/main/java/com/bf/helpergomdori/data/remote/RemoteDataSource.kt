package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.remote.DefaultHeader
import com.bf.helpergomdori.model.remote.body.PostUser
import com.bf.helpergomdori.model.remote.body.SigninBody
import com.bf.helpergomdori.model.remote.response.*
import com.bf.helpergomdori.utils.HEADER_KEY
import com.bf.helpergomdori.utils.HEADER_TOKEN_KEY
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

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
    suspend fun getHelpAccepted(@Header(HEADER_KEY) authorization: String, @Header(HEADER_TOKEN_KEY) token: String) // 도움 요청이 수락되었을 때 웹소켓으로 수락되었음을 받았을 때

    suspend fun getBfCntAndGomdoriCnt(): UserCnt // 베프 숫자 & 곰돌이 숫자

    suspend fun getHelpeePing(@Header(HEADER_KEY) header: String): HelpeePing

    suspend fun getHelperPing(@Header(HEADER_KEY) header: String): HelperPing
}