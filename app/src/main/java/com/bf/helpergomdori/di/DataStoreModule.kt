package com.bf.helpergomdori.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.handlers.ReplaceFileCorruptionHandler
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.SharedPreferencesMigration
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import com.bf.helpergomdori.UserInfo
import com.bf.helpergomdori.data.local.UserInfoSerializer
import com.bf.helpergomdori.data.repository.UserInfoRepository
import com.bf.helpergomdori.utils.DATA_STORE_FILE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Singleton
    @Provides
    fun provideUserInfoDataStore(@ApplicationContext appContext: Context): DataStore<UserInfo> {
        return DataStoreFactory.create(
            serializer = UserInfoSerializer,
            produceFile = {appContext.dataStoreFile(DATA_STORE_FILE_NAME)},
            corruptionHandler = null,
            scope = CoroutineScope((Dispatchers.IO + SupervisorJob())),
        )
    }

    @Singleton
    @Provides
    fun provideUserInfoRepository(dataStore: DataStore<UserInfo>): UserInfoRepository {
        return UserInfoRepository(dataStore)
    }
}