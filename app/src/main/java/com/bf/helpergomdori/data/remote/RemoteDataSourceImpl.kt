package com.bf.helpergomdori.data.remote

import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.remote.DefaultHeader
import com.bf.helpergomdori.model.remote.body.PostUser
import com.bf.helpergomdori.model.remote.body.SigninBody
import com.bf.helpergomdori.model.remote.response.HelpeeResponse
import com.bf.helpergomdori.model.remote.response.HelperResponse
import com.bf.helpergomdori.model.remote.response.Token
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
     * auth-controller
     */

    override suspend fun postMember(postUser: PostUser): Token {
        return apiService.postMember(postUser)
    }

    override suspend fun postHelpee(header: DefaultHeader, signinBody: SigninBody): HelpeeResponse {
        return apiService.postHelpee(header, signinBody)
    }

    override suspend fun postHelper(header: DefaultHeader): HelperResponse {
        return apiService.postHelper(header)
    }


}