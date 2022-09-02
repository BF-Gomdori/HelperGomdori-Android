package com.bf.helpergomdori.model.websocket


import org.json.JSONObject
import java.io.Serializable

data class WebSocketData(
    val type: String,
    val sub: String? = "main",
    val jwt: String,
    val location: LocationString // Location
):Serializable


enum class EnterType{
    ENTER, HELP, ACCEPT // ENTER: 비장애인, HELP : 장애인, ACCPET : 도움 수락했을 때
}

data class Location(
    var x: Double,
    var y: Double
)

data class LocationString(
    val x: String,
    val y: String
)
