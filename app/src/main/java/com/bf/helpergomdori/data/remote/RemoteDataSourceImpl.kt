package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.remote.DefaultHeader
import com.bf.helpergomdori.model.remote.body.NotificationBody
import com.bf.helpergomdori.model.remote.body.PostUser
import com.bf.helpergomdori.model.remote.body.SigninBody
import com.bf.helpergomdori.model.remote.response.*
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
    override suspend fun getHelpAccepted(authorization: String, token: String) {
        return apiService.getHelpAccepted(authorization, token)
    }

    override suspend fun getBfCntAndGomdoriCnt(): Flow<UserCnt> = flow {
        emit(apiService.getBfCntAndGomdoriCnt())
    }

    override suspend fun getHelpeePing(header: String): HelpeePing {
        return apiService.getHelpeePing(header)
    }

    override suspend fun getHelperPing(header: String): HelperPing {
        return apiService.getHelperPing(header)
    }

    override suspend fun postPush(header: String, body: NotificationBody): NotificationResponse {
        return apiService.postPush(header, body)
    }


}