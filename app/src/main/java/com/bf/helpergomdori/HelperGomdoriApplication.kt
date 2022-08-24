package com.bf.helpergomdori

import android.app.Application
import com.bf.helpergomdori.utils.NATIVE_APP_KEY
import com.kakao.sdk.common.KakaoSdk
import com.naver.maps.map.NaverMapSdk
import com.naver.maps.map.NaverMapSdk.NaverCloudPlatformClient
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class HelperGomdoriApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, NATIVE_APP_KEY)
    }
}