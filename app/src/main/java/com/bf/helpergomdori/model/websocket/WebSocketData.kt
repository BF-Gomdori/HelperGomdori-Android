package com.bf.helpergomdori.model.websocket


import org.json.JSONObject

data class WebSocketData(
    val type: String,
    val sub: String? = "main",
    val jwt: String,
    val location: JSONObject // Location
)

enum class EnterType{
    ENTER, HELP, ACCEPT
}

data class Location(
    val x: String,
    val y: String
)
