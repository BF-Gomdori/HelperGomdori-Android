package com.bf.helpergomdori.data.repository


import com.bf.helpergomdori.data.remote.RemoteDataSourceImpl
import com.bf.helpergomdori.model.Data
import com.bf.helpergomdori.model.remote.DefaultHeader
import com.bf.helpergomdori.model.remote.body.PostUser
import com.bf.helpergomdori.model.remote.body.SigninBody
import com.bf.helpergomdori.model.remote.response.Token
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSourceImpl,
    //private val externalScope: CoroutineScope
) {
    suspend fun getData(): Flow<Data> = remoteDataSource.getData()

    /**
     * auth-controller
     */

    suspend fun postUserInfo(postUser: PostUser): Token = remoteDataSource.postMember(postUser)

    suspend fun postHelpee(header: String, body: SigninBody) = remoteDataSource.postHelpee(header, body)

    suspend fun postHelper(header: String) = remoteDataSource.postHelper(header)
}

