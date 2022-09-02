package com.bf.helpergomdori.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.bf.helpergomdori.model.local.HelpType

class SharedPreferencesUtil(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(SHARED_PREFERENCE_NAME, 0)
    private var editor: SharedPreferences.Editor = prefs.edit()

    fun setJwt(jwt: String) {
        editor.putString(JWT_KEY, "Bearer $jwt").apply()
    }

    fun getJwt(): String {
        Log.d("JWT", "getJwt: ${prefs.getString(JWT_KEY, "")}")
        return prefs.getString(JWT_KEY, "")!!
    }

    fun setWebSocketJwt(jwt: String) {
        editor.putString(JWT_WEBSOCKET_KEY, jwt).apply()
    }

    fun getWebSocketJwt(): String {
       return prefs.getString(JWT_WEBSOCKET_KEY, "")!!
    }

    fun setFirebaseToken(token: String) {
        editor.putString(FIREBASE_TOKEN, token).apply()
    }

    fun getFirebaseToken(): String {
        return prefs.getString(FIREBASE_TOKEN, "")!!
    }

    fun setHelpType(type: HelpType) {
        editor.putString(HELP_TYPE_KEY, type.name).apply()
    }

    fun getHelpType(): HelpType {
        val type = prefs.getString(HELP_TYPE_KEY, HelpType.NONE.name)
        return when (type) {
            "BF" -> HelpType.BF
            "GOMDORI" -> HelpType.GOMDORI
            else -> HelpType.NONE
        }
    }
}