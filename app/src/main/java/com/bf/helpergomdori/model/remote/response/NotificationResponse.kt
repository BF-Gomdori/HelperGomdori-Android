package com.bf.helpergomdori.model.remote.response

import org.json.JSONObject

data class NotificationResponse(
    val body: JSONObject,
    val statusCode: String,
    val statusCodeValue: String
)
