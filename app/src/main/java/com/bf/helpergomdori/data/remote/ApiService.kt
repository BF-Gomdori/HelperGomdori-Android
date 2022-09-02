package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.remote.DefaultHeader
import com.bf.helpergomdori.model.remote.body.PostUser
import com.bf.helpergomdori.model.remote.body.SigninBody
import com.bf.helpergomdori.model.remote.response.HelpeeResponse
import com.bf.helpergomdori.model.remote.response.HelperResponse
import com.bf.helpergomdori.model.remote.response.Token
import com.bf.helpergomdori.utils.HEADER_KEY
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Header

interface ApiService {

    @GET("/users")
    suspend fun getData(): Data

    /**
     * auth-controller
     */

    @POST("/auth/barrierfree")
    suspend fun postMember(@Body postUser: PostUser) : Token

    @POST("/auth/signup/helpee")
    suspend fun postHelpee(@Header(HEADER_KEY) header: String, @Body signinBody: SigninBody): HelpeeResponse

    @POST("/auth/signup/helper")
    suspend fun postHelper(@Header(HEADER_KEY) header: String): HelperResponse
}