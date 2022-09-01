package com.bf.helpergomdori.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME,0)
    private var editor: SharedPreferences.Editor = prefs.edit()

    fun setJwt(jwt: String){
        editor.putString(JWT_KEY, "Bearer $jwt").apply()
    }

    fun getJwt(): String {
        return prefs.getString(JWT_KEY, "")!!
    }
}