package com.bf.helpergomdori

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import com.bf.helpergomdori.utils.NATIVE_APP_KEY
import com.bf.helpergomdori.utils.NotificationUtil
import com.bf.helpergomdori.utils.SharedPreferencesUtil
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.NaverMapSdk.NaverCloudPlatformClient
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class HelperGomdoriApplication: Application() {
    companion object {
        lateinit var PrefsUtil: SharedPreferencesUtil
    }

    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, NATIVE_APP_KEY)

        // sharedPreference 초기화
        PrefsUtil = SharedPreferencesUtil(this)

        // NotificationUtil 초기화
        NotificationUtil.setDefault(
            applicationContext,
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        )
    }
}