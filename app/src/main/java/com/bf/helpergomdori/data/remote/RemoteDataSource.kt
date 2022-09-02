package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.remote.DefaultHeader
import com.bf.helpergomdori.model.remote.body.PostUser
import com.bf.helpergomdori.model.remote.body.SigninBody
import com.bf.helpergomdori.model.remote.response.HelpeeResponse
import com.bf.helpergomdori.model.remote.response.HelperResponse
import com.bf.helpergomdori.model.remote.response.Token
import com.bf.helpergomdori.utils.HEADER_KEY
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface RemoteDataSource {

    suspend fun getData(): Flow<Data>

    /**
     * auth-controller
     */

    suspend fun postMember(@Body postUser: PostUser) : Token

    suspend fun postHelpee(@Header(HEADER_KEY) header: String, @Body signinBody: SigninBody): HelpeeResponse

    suspend fun postHelper(@Header(HEADER_KEY) header: String): HelperResponse
}