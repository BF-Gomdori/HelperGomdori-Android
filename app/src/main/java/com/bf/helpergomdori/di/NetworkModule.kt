package com.bf.helpergomdori.di

import com.bf.helpergomdori.data.remote.ApiService
import com.bf.helpergomdori.data.remote.RemoteDataSourceImpl
import com.bf.helpergomdori.data.repository.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val BASE_URL = "http://bc0e-219-255-158-173.ngrok.io"

    @Singleton
    @Provides
    fun provideOkHttpClient() : OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return OkHttpClient.Builder()
            .addNetworkInterceptor(httpLoggingInterceptor)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return  Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideRemoteDataSource(apiService: ApiService) : RemoteDataSourceImpl {
        return RemoteDataSourceImpl(apiService, Dispatchers.IO)
    }

    @Singleton
    @Provides
    fun provideRemoteRepository(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteRepository {
        return RemoteRepository(remoteDataSourceImpl)
    }
}