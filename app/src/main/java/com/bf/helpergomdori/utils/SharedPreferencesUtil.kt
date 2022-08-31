package com.bf.helpergomdori.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME,0)
    private var editor: SharedPreferences.Editor = prefs.edit()

    fun setJwt(jwt: String){
        editor.putString(JWT_KEY, jwt).apply()
    }

    fun getJwt() {
        prefs.getString(JWT_KEY, "")
    }
}