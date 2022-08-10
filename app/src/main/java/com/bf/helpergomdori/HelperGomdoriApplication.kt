package com.bf.helpergomdori

import android.app.Application
import com.bf.helpergomdori.utils.NATIVE_APP_KEY
import com.kakao.sdk.common.KakaoSdk

class HelperGomdoriApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        // Kakao SDK 초기화
        KakaoSdk.init(this, NATIVE_APP_KEY)
    }
}